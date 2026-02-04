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
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account account = new Account();
        account.setUser(user);
        account.setType(request.getType());
        account.setBalance(
                request.getInitialDeposit() != null ? request.getInitialDeposit() : 0.0
        );

        return accountRepository.save(account);
    }


    @Override
    public List<Account> getUserAccounts(Long userId) {
        return accountRepository.findByUserId(userId);
    }

    @Override
    public List<AccountSummaryDTO> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(acc -> new AccountSummaryDTO(
                        acc.getId(),
                        acc.getType(),
                        acc.getBalance(),
                        acc.getUser().getId(),
                        acc.getUser().getUsername()
                )).collect(Collectors.toList());
    }

    @Override
    public List<UserSummaryDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserSummaryDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getRole().toString()
                )).collect(Collectors.toList());
    }


    @Override
    @Transactional
    public void deleteUser(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        accountRepository.deleteAllByUserId(userId);
        userRepository.deleteById(userId);
    }
}
