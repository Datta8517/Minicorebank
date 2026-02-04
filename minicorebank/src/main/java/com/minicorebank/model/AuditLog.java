package com.minicorebank.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "audit_logs")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;          // null for anonymous / login failures
    private String action;        // LOGIN_SUCCESS, LOGIN_FAILED, TRANSFER, DEPOSIT, etc.

    @Column(length = 1000)
    private String details;       // human-readable message

    private String ipAddress;
    private String userAgent;
    private String httpMethod;
    private String requestUri;

    private boolean success;

    private LocalDateTime timestamp;

    public AuditLog() {}

    public AuditLog(Long userId,
                    String action,
                    String details,
                    String ipAddress,
                    String userAgent,
                    String httpMethod,
                    String requestUri,
                    boolean success) {
        this.userId = userId;
        this.action = action;
        this.details = details;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.httpMethod = httpMethod;
        this.requestUri = requestUri;
        this.success = success;
        this.timestamp = LocalDateTime.now();
    }

}

