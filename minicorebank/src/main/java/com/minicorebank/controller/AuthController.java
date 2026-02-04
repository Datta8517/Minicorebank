package com.minicorebank.controller;

import com.minicorebank.dto.*;
import com.minicorebank.model.*;
import com.minicorebank.repository.AccountRepository;
import com.minicorebank.repository.JwtBlacklistRepository;
import com.minicorebank.repository.RefreshTokenRepository;
import com.minicorebank.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

//@PreAuthorize("hasRole('CUSTOMER')")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserDeviceService userDeviceService;

    @Value("${security.maxFailedLoginAttempts}")
    private int maxFailedAttempts;

    @Value("${security.lockMinutes}")
    private int lockMinutes;

    @Autowired
    private JwtBlacklistRepository jwtBlacklistRepository;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRequest req, HttpServletRequest request) {
        User user = userService.register(req.getUsername(), req.getPassword(), Role.CUSTOMER);
        auditLogService.log(
                user.getId(),
                "REGISTER",
                "New User Registered: "+ user.getUsername(),
                request,
                true
        );
        return ResponseEntity.ok("User registered with id: " + user.getId());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req, HttpServletRequest request) {

        try{
            User user = userService.findByUsername(req.getUsername()).orElseThrow(() -> new RuntimeException("Invalid credentials"));

            // Check account lock
            if (!user.isAccountNonLocked()) {
                if (user.getLockUntil() != null && user.getLockUntil().isBefore(LocalDateTime.now())) {
                    // unlock
                    user.setAccountNonLocked(true);
                    user.setFailedLoginAttempts(0);
                    user.setLockUntil(null);
                } else {
                    throw new RuntimeException("Account locked. Try again later.");
                }
            }

            if (!passwordEncoder.matches(req.getPassword(), user.getPassword())){

                auditLogService.log(
                        user.getId(),
                        "LOGIN_FAILED",
                        "Wrong password for username: " + req.getUsername(),
                        request,
                        false
                );

                throw new RuntimeException("Invalid credentials");
            }

            if (user.isTwoFactorEnabled()) {
                if (req.getPin() == null ||
                        !passwordEncoder.matches(req.getPin(), user.getTwoFactorPinHash())) {
                    handleFailedLogin(user);
                    throw new RuntimeException("Invalid 2FA PIN");
                }
            }

            user.setFailedLoginAttempts(0);
            user.setAccountNonLocked(true);
            user.setLockUntil(null);
            userService.save(user); // You'll add save() in UserService


//            String token = jwtService.generateToken(user);

            String accessToken = jwtService.generateAccessToken(user);
            String refreshTokenValue = jwtService.generateRefreshToken(user);

            RefreshToken refreshToken = new RefreshToken();
            refreshToken.setUser(user);
            refreshToken.setToken(refreshTokenValue);

            refreshToken.setExpiresAt(LocalDateTime.now().plusSeconds(
                    // convert ms -> seconds
                    jwtService.extractAllClaims(refreshTokenValue).getExpiration().getTime() / 1000
            ));
            refreshTokenRepository.save(refreshToken);

            userDeviceService.trackLogin(user, request);

            AuthResponse authResponse = new AuthResponse();
            authResponse.setToken(accessToken);
            authResponse.setRefreshToken(refreshTokenValue);


            if (!user.isActive()) {
                throw new RuntimeException("User account is deactivated");
            }

            auditLogService.log(
                    user.getId(),
                    "LOGIN_SUCCESS",
                    "User logged in",
                    request,
                    true
            );

            return ResponseEntity.ok(authResponse);

        }
        catch (Exception ex){
            if(ex.getMessage().equals("Invalid credentials")) {
                auditLogService.log(
                        null,
                        "LOGIN FAILED",
                        "Invalid login attempt for username: "+req.getUsername(),
                        request,
                        false
                );
            }
            throw ex;

        }


    }

    private void handleFailedLogin(User user) {
        int attempts = user.getFailedLoginAttempts() + 1;
        user.setFailedLoginAttempts(attempts);
        if (attempts >= maxFailedAttempts) {
            user.setAccountNonLocked(false);
            user.setLockUntil(LocalDateTime.now().plusMinutes(lockMinutes));
        }
        userService.save(user);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshTokenRequest request) {

        String refreshTokenStr = request.getRefreshToken();
        if (refreshTokenStr == null || refreshTokenStr.isBlank()) {
            throw new RuntimeException("Refresh token missing");
        }

        RefreshToken rt = refreshTokenRepository.findByToken(refreshTokenStr)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (rt.isRevoked() || rt.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh token expired or revoked");
        }

        User user = rt.getUser();
        String newAccess = jwtService.generateAccessToken(user);

        AuthResponse res = new AuthResponse(newAccess, refreshTokenStr);
        return ResponseEntity.ok(res);
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader, @RequestBody LogoutRequest request) {

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String accessToken = authHeader.substring(7);

            Long userId = null;
            try {
                userId = Long.parseLong(jwtService.extractUserId(accessToken));
            } catch (Exception ignored) {}

            JwtBlacklistEntry entry = new JwtBlacklistEntry();
            entry.setToken(accessToken);
            entry.setUserId(userId);
            entry.setExpiresAt(
                    jwtService.extractAllClaims(accessToken)
                            .getExpiration()
                            .toInstant()
                            .atZone(java.time.ZoneId.systemDefault())
                            .toLocalDateTime()
            );
            entry.setReason("User logout");

            jwtBlacklistRepository.save(entry);
        }

        if (request.getRefreshToken() != null) {
            refreshTokenRepository.findByToken(request.getRefreshToken()).ifPresent(rt -> {
                rt.setRevoked(true);
                refreshTokenRepository.save(rt);
            });
        }

        return ResponseEntity.ok("Logged out successfully");
    }


    @PostMapping("/2fa/setup")
    public ResponseEntity<String> setup2fa(@RequestHeader("Authorization") String authHeader,
                                           @RequestBody TwoFactorSetupRequest req) {
        String pin = req.getPin(); // e.g., "1234"
        if (pin == null || pin.length() < 4) {
            throw new RuntimeException("PIN must be at least 4 digits");
        }

        Long userId = Long.parseLong(jwtService.extractUserId(authHeader.substring(7)));
        User user = userService.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!user.getId().equals(req.getUserId())){
            throw new RuntimeException("User id mismatch");
        }
        user.setTwoFactorEnabled(true);
        user.setTwoFactorPinHash(passwordEncoder.encode(pin));
        userService.save(user);

        return ResponseEntity.ok("2FA enabled successfully");
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestHeader("Authorization") String authHeader, @RequestBody TransactionRequest request) {
        Long userId = Long.parseLong(jwtService.extractUserId(authHeader.substring(7)));
        accountService.deposit(userId, request);
        return ResponseEntity.ok(request.getAmount()+" Deposit successful to account: "+ request.getAccountId());
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestHeader("Authorization") String authHeader,@RequestBody TransactionRequest request) {
        Long userId = Long.parseLong(jwtService.extractUserId(authHeader.substring(7)));
        accountService.withdraw(userId, request);
        return ResponseEntity.ok(request.getAmount()+ " Withdrawal successful from account: "+ request.getAccountId());
    }

    @GetMapping("/my-transactions")
    public ResponseEntity<List<TransactionRecord>> getAllUserTransaction() {
        return ResponseEntity.ok(transactionService.getCustomerTransaction());
    }

    @GetMapping("/my-transactions/last10")
    public ResponseEntity<List<TransactionRecord>> getLast10UserTransaction() {
        return ResponseEntity.ok(transactionService.getLast10Transactions());
    }


}