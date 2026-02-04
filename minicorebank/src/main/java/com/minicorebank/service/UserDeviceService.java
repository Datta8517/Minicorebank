package com.minicorebank.service;

import com.minicorebank.model.User;
import com.minicorebank.model.UserDevice;
import com.minicorebank.repository.UserDeviceRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserDeviceService {

    private final UserDeviceRepository repo;

    public UserDeviceService(UserDeviceRepository repo) {
        this.repo = repo;
    }

    public void trackLogin(User user, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String ua = request.getHeader("User-Agent");

        UserDevice device = new UserDevice();
        device.setUser(user);
        device.setIpAddress(ip);
        device.setUserAgent(ua);
        device.setLastLoginAt(LocalDateTime.now());

        repo.save(device); // simple: one row per login. You can later de-duplicate.
    }
}

