package com.minicorebank.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Document(collection = "user_devices")
public class UserDevice {

    @Id
    private String id;

    @Indexed(unique = true)
    private String deviceId;

    @Indexed
    private String userId;

    private String ipAddress;
    private String userAgent;

    @CreatedDate
    private Instant firstSeenAt;
    @LastModifiedDate
    private Instant lastLoginAt;

    private boolean active = true;
}
