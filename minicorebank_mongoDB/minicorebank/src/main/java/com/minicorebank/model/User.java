package com.minicorebank.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "users")
public class User {

    @Id
    private String id;   //

    @Indexed(unique = true)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private Role role;

    private boolean active;

    private boolean accountNonLocked;
    private int failedLoginAttempts;
    private LocalDateTime lockUntil;

    private boolean twoFactorEnabled;
    private String twoFactorPinHash;

    public User(String username, String password, Role role, boolean active) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.active = active;
    }

    public User() {}
}
