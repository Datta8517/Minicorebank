package com.minicorebank.dto;

public class TwoFactorSetupRequest {
    private String userId;
    private String pin;     // or otp-email-code

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getPin() { return pin; }
    public void setPin(String pin) { this.pin = pin; }
}
