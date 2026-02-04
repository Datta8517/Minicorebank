package com.minicorebank.service;

import com.minicorebank.model.Account;
import com.minicorebank.model.AccountStatus;
import com.minicorebank.model.TransactionRecord;
import com.minicorebank.model.User;
import com.minicorebank.repository.AccountRepository;
import com.minicorebank.repository.TransactionRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    @Transactional
    public TransactionRecord transfer(String authHeader, Long fromAccountId, Long toAccountId, Double amount, HttpServletRequest request) {

        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);

        Account from = accountRepository.findById(fromAccountId).orElseThrow(() -> new RuntimeException("From account not found"));
        Account to = accountRepository.findById(toAccountId).orElseThrow(() -> new RuntimeException("To account not found"));

        if(!from.getUser().getUsername().equals(username)){
            throw new RuntimeException("Kindly Login with correct Credentials");
        }

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

        auditLogService.log(
                from.getUser().getId(),
                "TRANSFER",
                "Transfer ₹" + amount+
                        " from A#" + fromAccountId +
                        " to A#" + toAccountId,
                request,
                true
        );

        // Record debit transaction for sender
        TransactionRecord debitRecord = new TransactionRecord();
        debitRecord.setUserId(from.getUser().getId());
        debitRecord.setFromAccountId(from.getId());
        debitRecord.setToAccountId(to.getId());
        debitRecord.setAmount(amount);
        debitRecord.setTimestamp(LocalDateTime.now());
        debitRecord.setType("DEBIT");
        transactionRepository.save(debitRecord);

        // Record credit transaction for receiver
        TransactionRecord creditRecord = new TransactionRecord();
        creditRecord.setUserId(to.getUser().getId());
        creditRecord.setFromAccountId(from.getId());
        creditRecord.setToAccountId(to.getId());
        creditRecord.setAmount(amount);
        creditRecord.setTimestamp(debitRecord.getTimestamp()); // same timestamp
        creditRecord.setType("CREDIT");
        transactionRepository.save(creditRecord);

        return debitRecord;
    }

    public List<TransactionRecord> getAccountTransactions(Long accountId) {
        return transactionRepository.findByFromAccountIdOrToAccountIdOrderByTimestampDesc(accountId, accountId);
    }

    public List<TransactionRecord> getCustomerTransaction() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        return transactionRepository.findByUserIdOrderByTimestampDesc(user.getId());
    }

    public List<TransactionRecord> getLast10Transactions(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        Pageable pageable = PageRequest.of(0,10);

        return transactionRepository.findByUserIdOrderByTimestampDesc(user.getId(), pageable).toList();
    }

    public List<TransactionRecord> getAllTransactions(){
        return transactionRepository.findAll();
    }

    public List<TransactionRecord> getUserTransactions(Long userId) {
        return transactionRepository.findByUserId(userId);
    }
}