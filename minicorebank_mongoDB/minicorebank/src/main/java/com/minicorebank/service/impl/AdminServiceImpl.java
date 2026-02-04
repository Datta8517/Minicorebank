package com.minicorebank.service.impl;

import com.minicorebank.dto.admin.AccountSummaryDTO;
import com.minicorebank.dto.admin.AdminCreateAccountRequest;
import com.minicorebank.dto.admin.AdminCreateUserRequest;
import com.minicorebank.dto.admin.UserSummaryDTO;
import com.minicorebank.model.Account;
import com.minicorebank.model.User;
import com.minicorebank.repository.AccountRepository;
import com.minicorebank.repository.UserRepository;
import com.minicorebank.service.AdminService;
import com.minicorebank.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Override
    public User createUser(AdminCreateUserRequest request) {

        if(userRepository.findByUsername(request.getUsername()).isPresent()){
            throw new RuntimeException("Username already exists");
        }

        return userService.register(request.getUsername(), request.getPassword(), request.getRole());
    }

    @Override
    public Account createAccount(AdminCreateAccountRequest request) {


        User user = userRepository.findById(String.valueOf(request.getUserId()))
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account account = new Account();
        account.setUserId(user.getId());
        account.setType(request.getType());
        account.setBalance(
                request.getInitialDeposit() != null ? request.getInitialDeposit() : 0.0
        );

        return accountRepository.save(account);
    }


    @Override
    public List<Account> getUserAccounts(String userId) {
        return accountRepository.findByUserId(userId).stream().toList();
    }

    @Override
    public List<AccountSummaryDTO> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(acc -> new AccountSummaryDTO(
                        acc.getId(),
                        acc.getType(),
                        acc.getBalance(),
                        acc.getUserId(),
                        userRepository.findById(acc.getUserId()).get().getUsername(),
                        acc.getStatus(),
                        acc.getApprovalStatus()
                )).collect(Collectors.toList());
    }

    @Override
    public List<UserSummaryDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserSummaryDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getRole().toString(),
                        user.isActive()
                )).collect(Collectors.toList());
    }


    @Override
    @Transactional
    public void deleteUser(String userId) {

        userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        accountRepository.deleteAllByUserId(userId);
        userRepository.deleteById(userId);
    }
}
