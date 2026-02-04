package com.minicorebank.repository;

import com.minicorebank.model.JwtBlacklistEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface JwtBlacklistRepository extends MongoRepository<JwtBlacklistEntry, String> {
    Optional<JwtBlacklistEntry> findByToken(String token);

    boolean existsByToken(String accessToken);
}
