package com.minicorebank.dto.admin;

import com.minicorebank.model.AccountStatus;
import lombok.Data;

@Data
public class AdminAccountStatusRequest {
    private Long accountId;
    private AccountStatus status;
}
