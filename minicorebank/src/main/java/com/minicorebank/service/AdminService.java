package com.minicorebank.service;

import com.minicorebank.dto.admin.AccountSummaryDTO;
import com.minicorebank.dto.admin.AdminCreateAccountRequest;
import com.minicorebank.dto.admin.AdminCreateUserRequest;
import com.minicorebank.dto.admin.UserSummaryDTO;
import com.minicorebank.model.Account;
import com.minicorebank.model.User;

import java.util.List;

public interface AdminService {

    User createUser(AdminCreateUserRequest request);

    Account createAccount(AdminCreateAccountRequest request);

    List<Account> getUserAccounts(Long userId);

    List<AccountSummaryDTO> getAllAccounts();
    List<UserSummaryDTO> getAllUsers();

    void deleteUser(Long userId);
}
