package com.minicorebank.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "accounts")
public class Account {

    @Id
    private String id;

    private String type;          // SAVINGS / CURRENT
    private Double balance = 0.0;

    @Indexed
    private String userId;

    @Indexed
    @Enumerated(EnumType.STRING)
    private AccountStatus status = AccountStatus.ACTIVE;
    @Indexed
    private String approvalStatus = "PENDING";
}
