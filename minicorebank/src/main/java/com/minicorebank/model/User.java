package com.minicorebank.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Data
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true, nullable = false)
    private String username;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;


    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean active = true;

    @Column(nullable = false)
    private boolean accountNonLocked = true;

    @Column(nullable = false)
    private int failedLoginAttempts = 0;

    private LocalDateTime lockUntil;

    @Column(nullable = false)
    private boolean twoFactorEnabled = false;

    // Stores BCrypt hash of 4/6 digit PIN
    private String twoFactorPinHash;


    public User(String username, String password, Role role,  boolean active) {
        this.username= username;
        this.password= password;
        this.role = role;
        this.active = active;
    }

    public User() {

    }


    // getters and setters
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//    public String getUsername() { return username; }
//    public void setUsername(String username) { this.username = username; }
//    public String getPassword() { return password; }
//    public void setPassword(String password) { this.password = password; }
//    public Role getRole() { return role; }
//    public void setRole(Role role) { this.role = role; }
//
//
//    public boolean isActive() {
//        return active;
//    }
//
//    public void setActive(boolean active) {
//        this.active = active;
//    }
}
