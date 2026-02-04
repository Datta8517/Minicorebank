package com.minicorebank.config;

import com.minicorebank.model.User;
import com.minicorebank.repository.JwtBlacklistRepository;
import com.minicorebank.repository.UserRepository;
import com.minicorebank.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtBlacklistRepository  jwtBlacklistRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        System.out.println("Auth Header: " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authHeader.substring(7);
        String username = null;

        if (jwtBlacklistRepository.findByToken(token).isPresent()) {
            // Token is blacklisted -> treat as unauthenticated
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"You are logged out. Please login again.\"}");
            return; //
        }

        String userId;
        try {
            username = jwtService.extractUsername(token);
            userId = (jwtService.extractUserId(token));
            System.out.println("Extracted Username: " + username);


        } catch (Exception e) {
            System.err.println("Token parsing error: " + e.getMessage());
            filterChain.doFilter(request, response);
            return;
        }

        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = userRepository.findById(userId).orElse(null);
            if (user != null  && user.isActive() && user.isAccountNonLocked()) {
                System.out.println("User found: " + user.getUsername() + ", Role: " + user.getRole());


                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user, null, List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())));


                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                System.out.println("User not found in DB.");
            }
        }
        filterChain.doFilter(request, response);
    }

}
