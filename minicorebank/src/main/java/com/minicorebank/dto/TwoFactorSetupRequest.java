package com.minicorebank.dto;

public class TwoFactorSetupRequest {
    private Long userId;
    private String pin;     // or otp-email-code

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getPin() { return pin; }
    public void setPin(String pin) { this.pin = pin; }
}
