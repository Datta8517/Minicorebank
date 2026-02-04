package com.minicorebank.repository;

import com.minicorebank.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AccountRepository extends MongoRepository<Account, String> {

    List<Account> findByUserId(String userId);                 // 🔁 Long → String

    boolean existsByUserIdAndType(String userId, String type); // 🔁 Long → String

    void deleteAllByUserId(String userId);                     // 🔁 Long → String

    List<Account> findByApprovalStatus(String approvalStatus); // 🟡 param case fixed
}
