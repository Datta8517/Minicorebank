package com.minicorebank.repository;

import com.minicorebank.model.AuditLog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AuditLogRepository extends MongoRepository<AuditLog, String> {

    List<AuditLog> findByUserIdOrderByCreatedAtDesc(String userId);

}
