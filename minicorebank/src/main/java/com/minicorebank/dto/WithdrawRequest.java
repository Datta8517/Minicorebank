package com.minicorebank.dto;


public class WithdrawRequest {

    private Long accountId;
    private Double amount;

    public WithdrawRequest() {}

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
