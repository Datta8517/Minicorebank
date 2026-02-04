package com.minicorebank.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Document(collection = "transactions")
@CompoundIndexes({
        @CompoundIndex(
                name = "user_timestamp_idx",
                def = "{ 'userId': 1, 'timestamp': -1 }"
        ),
        @CompoundIndex(
                name = "account_timestamp_idx",
                def = "{ 'fromAccountId': 1, 'timestamp': -1 }"
        )
})
public class TransactionRecord {

    @Id
    private String id;

    @Indexed
    private String userId;
    @Indexed
    private String username;
    private String transferId;
    private String fromAccountId;
    private String toAccountId;

    private Double amount;
    @CreatedDate
    private Instant createdAt;
    private String type;
}
