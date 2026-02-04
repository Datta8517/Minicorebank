package com.minicorebank.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCreatedEvent {

    private String transactionId;
    private String transferId;
    private String userId;
    private String fromAccountId;
    private String toAccountId;
    private Double amount;
    private String type; // DEBIT / CREDIT / TRANSFER
    private Instant timestamp;
}
