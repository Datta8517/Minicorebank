package com.minicorebank.repository;

import com.minicorebank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByUserId(Long userId);

    boolean existsByUserIdAndType(Long userId, String type);
    void deleteAllByUserId(Long userId);
    List<Account> findByApprovalStatus(String ApprovalStatus);

}