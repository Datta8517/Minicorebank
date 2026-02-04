package com.minicorebank.dto.admin;

import lombok.Data;

@Data
public class AdminUserStatusRequest {
    private Long userId;
    private boolean active;
}
