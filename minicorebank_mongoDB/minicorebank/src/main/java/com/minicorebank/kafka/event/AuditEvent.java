package com.minicorebank.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditEvent {

    private String userId;
    private String action;
    private String details;
    private String ipAddress;
    private boolean success;
    private Instant timestamp;
}
