package com.minicorebank.dto.admin;

import lombok.Data;

@Data
public class UserSummaryDTO {
    private String Id;
    private String username;
    private String role;
    private boolean active;

    public UserSummaryDTO(String Id, String username, String role, boolean active) {
        this.Id = Id;
        this.username = username;
        this.role = role;
        this.active=active;
    }
}
