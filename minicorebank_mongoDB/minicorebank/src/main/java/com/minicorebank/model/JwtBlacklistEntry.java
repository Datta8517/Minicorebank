package com.minicorebank.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Document(collection = "jwt_blacklist")

public class JwtBlacklistEntry {

    @Id
    private String id;

    @Indexed(unique = true)
    private String token;

    private Instant blacklistedAt = Instant.now();

    @Indexed(expireAfter = "0s")
    private Instant expiresAt;

    private String userId;
    private String reason;
}
