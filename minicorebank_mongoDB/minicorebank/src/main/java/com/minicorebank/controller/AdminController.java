package com.minicorebank.controller;

import com.minicorebank.dto.WithdrawRequest;
import com.minicorebank.dto.admin.*;
import com.minicorebank.model.Account;
import com.minicorebank.model.TransactionRecord;
import com.minicorebank.model.User;
import com.minicorebank.service.AccountService;
import com.minicorebank.service.AdminService;
import com.minicorebank.service.JwtService;
import com.minicorebank.service.TransactionService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final TransactionService transactionService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody AdminCreateUserRequest request) {
        return ResponseEntity.ok(adminService.createUser(request));
    }

    @PostMapping("/create-accounts")
    public ResponseEntity<Account> createAccount(@RequestBody AdminCreateAccountRequest request) {
        return ResponseEntity.ok(adminService.createAccount(request));
    }

    @GetMapping("/users/{userId}/transactions")
    public ResponseEntity<List<TransactionRecord>> getAllUserTransaction(@PathVariable String userId) {
        return ResponseEntity.ok(transactionService.getUserTransactions(userId));
    }

    @GetMapping("/all-transactions")
    public ResponseEntity<List<TransactionSummaryDTO>> getAllTransaction() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        adminService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("/all-accounts")
    public ResponseEntity<List<AccountSummaryDTO>> getAllAccounts() {
        return ResponseEntity.ok(adminService.getAllAccounts());
    }

    @GetMapping("/all-users")
    public ResponseEntity<List<UserSummaryDTO>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }


    @PutMapping("/user/status")
    public ResponseEntity<User> adminUserUpdateRequest(@RequestHeader("Authorization") String authHeader, @RequestBody AdminUserStatusRequest req){
        String token = authHeader.substring(7);
        Claims claims = jwtService.extractAllClaims(token);
        String role = claims.get("role", String.class);

        if(role.equals("ADMIN")){
            User user = accountService.updateUserStatus(req.getUserId(), req.isActive());
            return ResponseEntity.ok(user);
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }



}
