package com.minicorebank.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Document(collection = "audit_logs")
@CompoundIndex(
        name = "audit_user_timestamp_idx",
        def = "{ 'userId': 1, 'timestamp': -1 }"
)
public class AuditLog {

    @Id
    private String id;

    @Indexed
    private String userId;
    private String action;
    private String details;

    private String ipAddress;
    private String userAgent;
    private String httpMethod;
    private String requestUri;

    private boolean success;
    @CreatedDate
    private Instant createdAt;

    public AuditLog() {}

    public AuditLog(String userId,
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
    }
}
