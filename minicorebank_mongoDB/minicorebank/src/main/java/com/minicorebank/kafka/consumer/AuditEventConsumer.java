package com.minicorebank.kafka.consumer;

import com.minicorebank.kafka.event.AuditEvent;
import com.minicorebank.kafka.event.TransactionCreatedEvent;
import com.minicorebank.model.AuditLog;
import com.minicorebank.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuditEventConsumer {

    private final AuditLogRepository auditLogRepository;

    @KafkaListener(
            topics = "transaction-created",
            groupId = "minicorebank-group"
    )
    public void consume(TransactionCreatedEvent event) {

        AuditLog log = new AuditLog();

        log.setUserId(event.getUserId());
        log.setAction(resolveAction(event));
        log.setDetails(buildDetails(event));
        log.setSuccess(true);
//        log.setTimestamp(event.getTimestamp());

        auditLogRepository.save(log);
    }

    private String resolveAction(TransactionCreatedEvent event) {
        return "TRANSACTION_" + event.getType();
    }

    private String buildDetails(TransactionCreatedEvent event) {
        return String.format(
                "TxnId=%s, Amount=%.2f, From=%s, To=%s",
                event.getTransactionId(),
                event.getAmount(),
                event.getFromAccountId(),
                event.getToAccountId()
        );
    }
}
