package com.minicorebank.service;

import com.minicorebank.dto.admin.AccountSummaryDTO;
import com.minicorebank.dto.admin.AdminCreateAccountRequest;
import com.minicorebank.dto.admin.AdminCreateUserRequest;
import com.minicorebank.dto.admin.UserSummaryDTO;
import com.minicorebank.model.Account;
import com.minicorebank.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AdminService {

    User createUser(AdminCreateUserRequest request);

    Account createAccount(AdminCreateAccountRequest request);


    List<Account> getUserAccounts(String userId);

    List<AccountSummaryDTO> getAllAccounts();
    List<UserSummaryDTO> getAllUsers();

    @Transactional
    void deleteUser(String userId);
}
