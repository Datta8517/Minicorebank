package com.minicorebank.dto.admin;

import com.minicorebank.model.AccountStatus;
import lombok.Data;

@Data
public class AdminAccountStatusRequest {
    private String accountId;
    private AccountStatus status;
}
