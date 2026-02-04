package com.minicorebank.repository;

import com.minicorebank.model.TransactionRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionRepository
        extends MongoRepository<TransactionRecord, String> {

    List<TransactionRecord>
    findByFromAccountIdOrToAccountIdOrderByCreatedAtDesc(
            String fromAccountId,
            String toAccountId
    );

    List<TransactionRecord> findByUserIdOrderByCreatedAtDesc(String userId);

    Page<TransactionRecord> findByUserIdOrderByCreatedAtDesc(String userId, Pageable pageable);

    List<TransactionRecord> findByUserId(String userId);
}
