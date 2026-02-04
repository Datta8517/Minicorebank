package com.minicorebank.dto;

public class TransactionRequest {

    private String accountId;
    private Double amount;

    public TransactionRequest() {}

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
