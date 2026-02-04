package com.minicorebank.service;

import com.minicorebank.dto.admin.AccountSummaryDTO;
import com.minicorebank.dto.admin.TransactionSummaryDTO;
import com.minicorebank.kafka.producer.TransactionEventProducer;
import com.minicorebank.model.Account;
import com.minicorebank.model.AccountStatus;
import com.minicorebank.model.TransactionRecord;
import com.minicorebank.model.User;
import com.minicorebank.repository.AccountRepository;
import com.minicorebank.repository.TransactionRepository;
import com.minicorebank.kafka.event.TransactionCreatedEvent;
import com.minicorebank.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private TransactionEventProducer transactionEventProducer;

    @Autowired
    private PasswordEncoder passwordEncoder;
    private void validate2FA(User user, String pin) {
        if (user.isTwoFactorEnabled()) {
            if (pin == null || !passwordEncoder.matches(pin, user.getTwoFactorPinHash())) {
                throw new RuntimeException("Invalid 2FA PIN");
            }
        }
    }


    @Transactional
    public TransactionRecord transfer(String authHeader, String fromAccountId, String toAccountId, Double amount, String pin, HttpServletRequest request) {

        String token = authHeader.substring(7);
        String userId = jwtService.extractUserId(token);

        Account from = accountRepository.findById(fromAccountId).orElseThrow(() -> new RuntimeException("From account not found"));
        Account to = accountRepository.findById(toAccountId).orElseThrow(() -> new RuntimeException("To account not found"));

        if(!from.getUserId().equals(userId)){
            throw new RuntimeException("Kindly Login with correct Credentials");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        validate2FA(user, pin);

        if (amount == null || amount <= 0) throw new IllegalArgumentException("Invalid amount");
        if (fromAccountId.equals(toAccountId)) throw new IllegalArgumentException("Cannot transfer to same account");


        if (from.getBalance() < amount) throw new RuntimeException("Insufficient balance");

        if(from.getStatus().equals(AccountStatus.FROZEN) || to.getStatus().equals(AccountStatus.FROZEN) ) {
            throw new RuntimeException("One of the Account is frozen. Action not allowed.");
        }
        if(from.getStatus().equals(AccountStatus.CLOSED) || to.getStatus().equals(AccountStatus.CLOSED)) {
            throw new RuntimeException("One of the Account is CLOSED. Action not allowed.");
        }

        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);

        accountRepository.save(from);
        accountRepository.save(to);
        String transferId = UUID.randomUUID().toString();


        auditLogService.log(
                from.getUserId(),
                "TRANSFER",
                "Transfer ₹" + amount+
                        " from A#" + fromAccountId +
                        " to A#" + toAccountId,
                request,
                true
        );


        // Record debit transaction for sender
        TransactionRecord debitRecord = new TransactionRecord();
        debitRecord.setUserId(from.getUserId());
        debitRecord.setFromAccountId(from.getId());
        debitRecord.setToAccountId(to.getId());
        debitRecord.setAmount(amount);
        debitRecord.setType("DEBIT");
        debitRecord.setTransferId(transferId);
        debitRecord.setCreatedAt(Instant.now());

        TransactionRecord savedDebit = transactionRepository.save(debitRecord);

        // Record credit transaction for receiver
        TransactionRecord creditRecord = new TransactionRecord();
        creditRecord.setUserId(to.getUserId());
        creditRecord.setFromAccountId(from.getId());
        creditRecord.setToAccountId(to.getId());
        creditRecord.setAmount(amount);
        creditRecord.setType("CREDIT");
        creditRecord.setTransferId(transferId);
        creditRecord.setCreatedAt(Instant.now());

        TransactionRecord savedCredit =transactionRepository.save(creditRecord);

        transactionEventProducer.send(
                new TransactionCreatedEvent(
                        savedDebit.getId(),
                        savedDebit.getTransferId(),
                        savedDebit.getUserId(),
                        savedDebit.getFromAccountId(),
                        savedDebit.getToAccountId(),
                        savedDebit.getAmount(),
                        savedDebit.getType(),
                        savedDebit.getCreatedAt()
                )
        );

        transactionEventProducer.send(
                new TransactionCreatedEvent(
                        savedCredit.getId(),
                        savedCredit.getTransferId(),
                        savedCredit.getUserId(),
                        savedCredit.getFromAccountId(),
                        savedCredit.getToAccountId(),
                        savedCredit.getAmount(),
                        savedCredit.getType(),
                        savedCredit.getCreatedAt()
                )
        );


        return debitRecord;
    }

    public List<TransactionRecord> getAccountTransactions(String accountId) {
        return transactionRepository.findByFromAccountIdOrToAccountIdOrderByCreatedAtDesc(accountId, accountId);
    }

    public List<TransactionRecord> getCustomerTransaction() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        return transactionRepository.findByUserIdOrderByCreatedAtDesc(user.getId());
    }

    public List<TransactionRecord> getLast10Transactions(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        Pageable pageable = PageRequest.of(0,10);

        return transactionRepository.findByUserIdOrderByCreatedAtDesc(user.getId(), pageable).toList();
    }

    @Autowired
    UserRepository userRepository;
    public List<TransactionSummaryDTO> getAllTransactions(){

        return transactionRepository.findAll().stream()
                .map(txn -> new TransactionSummaryDTO(
                        txn.getId(),
                        txn.getUserId(),
                        userRepository.findById(txn.getUserId()).get().getUsername(),
                        txn.getTransferId(),
                        txn.getFromAccountId(),
                        txn.getToAccountId(),
                        txn.getAmount(),
                        txn.getCreatedAt(),
                        txn.getType()
                )).collect(Collectors.toList());
    }

    public List<TransactionRecord> getUserTransactions(String userId) {
        return transactionRepository.findByUserId(userId);
    }
}