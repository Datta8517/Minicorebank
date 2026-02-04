package com.minicorebank.repository;

import com.minicorebank.model.TransactionRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;


public interface TransactionRepository extends JpaRepository<TransactionRecord, Long> {
    List<TransactionRecord> findByFromAccountIdOrToAccountIdOrderByTimestampDesc(Long from, Long to);

    // ALL transactions of the logged-in user
    List<TransactionRecord> findByUserIdOrderByTimestampDesc(Long userId);

    // Only last 10 transactions
    Page<TransactionRecord> findByUserIdOrderByTimestampDesc(Long userId, Pageable pageable);

    List<TransactionRecord> findByUserId(Long userId);
}
