package com.minicorebank.dto.admin;

import com.minicorebank.model.AccountStatus;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.Instant;

@Data
public class TransactionSummaryDTO {
    private String id;
    private String userId;
    private String username;
    private String transferId;
    private String fromAccountId;
    private String toAccountId;

    private Double amount;
    private Instant createdAt;
    private String type;

    public TransactionSummaryDTO(String id, String userId, String username, String transferId, String fromAccountId, String toAccountId, Double amount, Instant createdAt,String type) {
        this.id=id;
        this.userId= userId;
        this.username= username;
        this.transferId= transferId;
        this.fromAccountId=fromAccountId;
        this.toAccountId=toAccountId;
        this.amount= amount;
        this.createdAt= createdAt;
        this.type=type;

    }
}
