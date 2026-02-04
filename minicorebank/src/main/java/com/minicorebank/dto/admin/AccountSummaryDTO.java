package com.minicorebank.dto.admin;

import lombok.Data;

@Data
public class AccountSummaryDTO {
    private Long id;
    private String type;
    private double balance;
    private Long userId;
    private String username;

    public AccountSummaryDTO(Long id, String type, double balance, Long userId, String username) {
        this.id = id;
        this.type = type;
        this.balance = balance;
        this.userId = userId;
        this.username = username;

    }
}
