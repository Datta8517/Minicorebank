package com.minicorebank.dto.admin;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AdminUserStatusRequest {
    private String userId;
    @Getter
    @Setter
    private boolean active;

}
