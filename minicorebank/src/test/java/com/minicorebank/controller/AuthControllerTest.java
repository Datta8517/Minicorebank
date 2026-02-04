package com.minicorebank.controller;

import com.minicorebank.dto.*;
import com.minicorebank.model.User;
import com.minicorebank.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setup() { MockitoAnnotations.openMocks(this); }

    @Test
    void testLoginSuccess() {
        AuthRequest req = new AuthRequest(); req.setUsername("john"); req.setPassword("pass");
        User user = new User(); user.setUsername("john"); user.setPassword("encoded");
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(userService.findByUsername("john")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("pass", "encoded")).thenReturn(true);
        when(jwtService.generateAccessToken(user)).thenReturn("token123");

        ResponseEntity<AuthResponse> res = authController.login(req,request);
        assertEquals(200, res.getStatusCodeValue());
        assertEquals("token123", res.getBody().getToken());
    }
}