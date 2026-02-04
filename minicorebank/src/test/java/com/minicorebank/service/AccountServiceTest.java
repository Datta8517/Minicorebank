package com.minicorebank.service;

import com.minicorebank.dto.AccountRequest;
import com.minicorebank.model.*;
import com.minicorebank.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setup() { MockitoAnnotations.openMocks(this); }

    @Test
    void testCreateAccount() {
        User user = new User(); user.setId(1L); user.setUsername("john");
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));
        when(accountRepository.save(any(Account.class))).thenAnswer(inv -> inv.getArgument(0));

//        AccountRequest req = new AccountRequest(); req.setType("SAVINGS"); req.setInitialDeposit(1000.0);
//        Account acc = accountService.createAccount("john", req);
//
//        assertEquals("SAVINGS", acc.getType());
//        assertEquals(1000.0, acc.getBalance());
//        assertEquals(user, acc.getUser());
    }
}
