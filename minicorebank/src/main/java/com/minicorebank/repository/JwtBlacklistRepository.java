package com.minicorebank.repository;

import com.minicorebank.model.JwtBlacklistEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JwtBlacklistRepository extends JpaRepository<JwtBlacklistEntry, Long> {
    Optional<JwtBlacklistEntry> findByToken(String token);
}
