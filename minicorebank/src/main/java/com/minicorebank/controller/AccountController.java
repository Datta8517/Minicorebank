package com.minicorebank.controller;

import com.minicorebank.dto.AccountRequest;
import com.minicorebank.dto.admin.AdminAccountStatusRequest;
import com.minicorebank.dto.admin.AdminUserStatusRequest;
import com.minicorebank.model.Account;
import com.minicorebank.model.Role;
import com.minicorebank.model.User;
import com.minicorebank.service.AccountService;
import com.minicorebank.service.AuditLogService;
import com.minicorebank.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuditLogService auditLogService;

    @PostMapping("/create")
    public ResponseEntity<Account> create(@RequestHeader("Authorization") String authHeader, @RequestBody AccountRequest req, HttpServletRequest request) {
        String token = authHeader.substring(7);
        Claims claims = jwtService.extractAllClaims(token);
//        Long userId = Long.valueOf(claims.get("userId").toString());
        String userId = jwtService.extractUserId(token);
        Account account = accountService.createAccount(Long.parseLong(userId),req);
        auditLogService.log(
                Long.parseLong(userId),
                "ACCOUNT CREATED",
                "User created account ID: "+account.getId()+" type= "+ account.getType(),
                request,
                true
        );


        return ResponseEntity.ok(account);
    }

    @GetMapping("/my-account")
    public ResponseEntity<List<Account>> myAccounts(@RequestHeader("Authorization") String authHeader, HttpServletRequest request) {
        String token = authHeader.substring(7);
        Claims claims = jwtService.extractAllClaims(token);
//        Long userId = Long.valueOf(claims.get("userId").toString());
        String userId = jwtService.extractUserId(token);
        auditLogService.log(
                Long.parseLong(userId),
                "VIEW_ACCOUNTS",
                "User viewed their accounts",
                request,
                true
        );


        return ResponseEntity.ok(accountService.getUserAccounts(Long.parseLong(userId)));
    }

    @PutMapping("/account/status")
    public ResponseEntity<Account> adminAccountUpdateRequest(@RequestHeader("Authorization") String authHeader, @RequestBody AdminAccountStatusRequest req,
                                                             HttpServletRequest request) {
        String token = authHeader.substring(7);
        Claims claims = jwtService.extractAllClaims(token);
        String role = claims.get("role",String.class);
//        System.out.println("role is:" + role );

        if(role.equals("ADMIN")){
//            System.out.println("Account is "+req.getStatus());
            Account account = accountService.updateAccountStatus(req.getAccountId(), req.getStatus());
            auditLogService.log(
                    account.getUser().getId(),
                    "USER ACCOUNT UPDATED",
                    "Account ID: "+account.getId()+" changed to "+req.getStatus(),
                    request,
                    true
            );
            return ResponseEntity.ok(account);
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PutMapping("/approve/{accountId}")
    public ResponseEntity<String> approveAccount(@RequestHeader("Authorization") String authHeader, @PathVariable Long accountId, HttpServletRequest request) {

        Long userId = Long.parseLong(jwtService.extractUserId(authHeader.substring(7)));
        String role = jwtService.extractAllClaims(authHeader.substring(7))
                .get("role", String.class);

        if (!"ADMIN".equals(role)) {
            return ResponseEntity.status(403).body("Only admin can approve accounts");
        }
        String approve = accountService.approveAccount(accountId);

        if(approve.equalsIgnoreCase("approved")) {
            auditLogService.log(
                    userId,
                    "USER ACCOUNT APPROVED",
                    "Account ID: "+accountId+" Activated ",
                    request,
                    true
            );
            return ResponseEntity.status(200).body("Account approved");
        }
        return ResponseEntity.status(403).body("FORBIDDEN");
    }

    // ADMIN — get all pending accounts
    @GetMapping("/pending")
    public ResponseEntity<List<Account>> pendingAccounts() {
        return ResponseEntity.ok(accountService.getPendingAccounts());
    }



}