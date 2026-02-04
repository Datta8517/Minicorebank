package com.minicorebank.dto.admin;

import com.minicorebank.model.Role;
import lombok.Data;

@Data
public class AdminCreateUserRequest {
    private String username;
    private String password;
    private Role role; // ADMIN or CUSTOMER
}
