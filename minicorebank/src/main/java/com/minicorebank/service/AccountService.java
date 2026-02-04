package com.minicorebank.service;

import com.minicorebank.dto.AccountRequest;
import com.minicorebank.dto.TransactionRequest;
import com.minicorebank.model.Account;
import com.minicorebank.model.AccountStatus;
import com.minicorebank.model.TransactionRecord;
import com.minicorebank.model.User;
import com.minicorebank.repository.AccountRepository;
import com.minicorebank.repository.TransactionRepository;
import com.minicorebank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;


    public Account createAccount(Long userId, AccountRequest req) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the user already has this type of account
        boolean exists = accountRepository.existsByUserIdAndType(user.getId(), req.getType());

        if (exists) {
            throw new RuntimeException(
                    "User already has a " + req.getType() + " account"
            );
        }
        Account account = new Account();
        account.setType(req.getType());
        account.setBalance(req.getInitialDeposit() == null ? 0.0 : req.getInitialDeposit());
        account.setUser(user);
        account.setStatus(AccountStatus.INACTIVE);
        account.setApprovalStatus("PENDING");
        return accountRepository.save(account);
    }


    public List<Account> getUserAccounts(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return accountRepository.findByUserId(user.getId());
    }

    public Account checkAccount(Long loginId, TransactionRequest request){
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if(!account.getUser().getId().equals(loginId)) {
            throw new RuntimeException("Unauthorized: You can only access your own account");
        }

        if(account.getStatus().equals(AccountStatus.FROZEN)) {
            throw new RuntimeException("Account is frozen. Action not allowed.");
        }
        if(account.getStatus().equals(AccountStatus.CLOSED)) {
            throw new RuntimeException("Account is CLOSED. Action not allowed.");
        }

        if(account.getStatus().equals(AccountStatus.INACTIVE)) {
            throw new RuntimeException("Account is INACTIVE. Action not allowed. Kindly Activate your Account");
        }

        return account;
    }

    public void deposit(Long userId, TransactionRequest req) {
        Account account = checkAccount(userId, req);

        account.setBalance(account.getBalance() + req.getAmount());

        TransactionRecord tr = new TransactionRecord();
        tr.setFromAccountId(null);
        tr.setToAccountId(account.getId());
        tr.setAmount(req.getAmount());
        tr.setTimestamp(LocalDateTime.now());
        tr.setType("DEPOSIT");
        tr.setUserId(account.getUser().getId());

        transactionRepository.save(tr);
        accountRepository.save(account);
    }


    public void withdraw(Long userId, TransactionRequest req) {
        Account account = checkAccount(userId, req);

        account.setBalance(account.getBalance() - req.getAmount());

        TransactionRecord tr = new TransactionRecord();
        tr.setFromAccountId(account.getId());
        tr.setToAccountId(null);
        tr.setAmount(req.getAmount());
        tr.setTimestamp(LocalDateTime.now());
        tr.setType("WITHDRAW");
        tr.setUserId(account.getUser().getId());

        transactionRepository.save(tr);

        accountRepository.save(account);
    }

    public Account updateAccountStatus(Long accountId, AccountStatus status) {
        System.out.println("Account is "+status);
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        if(account.getStatus().equals(status)) {
            throw new RuntimeException("Account is Already "+status);
        }
        account.setStatus(status);
        return accountRepository.save(account);
    }

    public User updateUserStatus(Long userId, boolean active){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found"));

        user.setActive(active);
        return userRepository.save(user);
    }

    //admin only
    public List<Account> getPendingAccounts() {
        return accountRepository.findByApprovalStatus("PENDING");
    }

    //admin only
    public String approveAccount(Long accountId) {
        Account acc = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        User user =  acc.getUser();
        boolean userStatus = user.isActive();
        System.out.println("User "+userStatus);
        if(userStatus) {
            acc.setApprovalStatus("APPROVED");
            acc.setStatus(AccountStatus.ACTIVE);
            accountRepository.save(acc);
            return "APPROVED";
        }
        else{
            acc.setApprovalStatus("REJECTED");
            return "REJECTED";
        }
    }

}
