package com.minicorebank.dto.admin;

import com.minicorebank.model.AccountStatus;
import lombok.Data;

@Data
public class AccountSummaryDTO {
    private String id;
    private String type;
    private double balance;
    private String userId;
    private String username;
    private AccountStatus status;
    private String approvalStatus;

    public AccountSummaryDTO(String id, String type, double balance, String userId, String username,AccountStatus status, String approvalStatus) {
        this.id = id;
        this.type = type;
        this.balance = balance;
        this.userId = userId;
        this.username = username;
        this.status = status;
        this.approvalStatus = approvalStatus;

    }
}
