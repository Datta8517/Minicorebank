package com.minicorebank.service;

import com.minicorebank.model.User;
import com.minicorebank.model.UserDevice;
import com.minicorebank.repository.UserDeviceRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserDeviceService {

    private final UserDeviceRepository repo;

    public UserDeviceService(UserDeviceRepository repo) {
        this.repo = repo;
    }

    public void trackLogin(User user, HttpServletRequest request) {

        String deviceId = request.getHeader("X-Device-Id");

        if (deviceId == null || deviceId.isBlank()) {
            deviceId = user.getId() + "-" +
                    request.getRemoteAddr() + "-" +
                    request.getHeader("User-Agent");
        }

        Optional<UserDevice> existing =
                repo.findByDeviceId(deviceId);

        UserDevice device;

        if (existing.isPresent()) {
            // 🔁 SAME DEVICE → UPDATE
            device = existing.get();
            device.setLastLoginAt(Instant.now());
            device.setActive(true);
        } else {
            // 🆕 NEW DEVICE → INSERT
            device = new UserDevice();
            device.setDeviceId(deviceId);
            device.setUserId(user.getId());
            device.setIpAddress(request.getRemoteAddr());
            device.setUserAgent(request.getHeader("User-Agent"));
            device.setFirstSeenAt(Instant.now());
            device.setLastLoginAt(Instant.now());
            device.setActive(true);
        }

        repo.save(device);
    }

}

