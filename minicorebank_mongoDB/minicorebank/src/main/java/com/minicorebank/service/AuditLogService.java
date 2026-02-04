package com.minicorebank.service;

import com.minicorebank.model.AuditLog;
import com.minicorebank.repository.AuditLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class AuditLogService {

    private final AuditLogRepository repo;

    public AuditLogService(AuditLogRepository repo) {
        this.repo = repo;
    }

    public void log(String userId,
                    String action,
                    String details,
                    HttpServletRequest request,
                    boolean success) {

        String ip = request.getRemoteAddr();
        String ua = request.getHeader("User-Agent");
        String method = request.getMethod();
        String uri = request.getRequestURI();

        AuditLog log = new AuditLog(
                userId,
                action,
                details,
                ip,
                ua,
                method,
                uri,
                success
        );
        repo.save(log);
    }
}

