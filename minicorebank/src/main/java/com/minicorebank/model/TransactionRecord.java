package com.minicorebank.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
public class TransactionRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Transactions id

    private Long userId;
    private Long fromAccountId;
    private Long toAccountId;
    private Double amount;
    private LocalDateTime timestamp;
    private String type; // TRANSFER, CREDIT, DEBIT


    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getFromAccountId() { return fromAccountId; }
    public void setFromAccountId(Long fromAccountId) { this.fromAccountId = fromAccountId; }
    public Long getToAccountId() { return toAccountId; }
    public void setToAccountId(Long toAccountId) { this.toAccountId = toAccountId; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getUserId() { return userId; }
}
