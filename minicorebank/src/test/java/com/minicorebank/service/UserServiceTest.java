package com.minicorebank.service;

import com.minicorebank.model.User;
import com.minicorebank.model.Role;
import com.minicorebank.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        when(passwordEncoder.encode(anyString())).thenReturn("encoded");
        User saved = new User();
        saved.setUsername("john");
        saved.setRole(Role.CUSTOMER);
        when(userRepository.save(any(User.class))).thenReturn(saved);

        User result = userService.register("john", "pass", Role.CUSTOMER);
        assertNotNull(result);
        assertEquals("john", result.getUsername());
        verify(userRepository, times(1)).save(any(User.class));
    }
}