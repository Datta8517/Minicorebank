package com.minicorebank.dto;

import jakarta.validation.constraints.NotBlank;

public class LogoutRequest {

    @NotBlank(message = "Refresh token is required")
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }
    public void setRefreshToken(String token) {
        this.refreshToken = token;
    }
}
