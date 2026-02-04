package com.minicorebank.dto.admin;

import lombok.Data;

@Data
public class UserSummaryDTO {
    private Long Id;
    private String username;
    private String role;

    public UserSummaryDTO(Long Id, String username, String role) {
        this.Id = Id;
        this.username = username;
        this.role = role;
    }
}
