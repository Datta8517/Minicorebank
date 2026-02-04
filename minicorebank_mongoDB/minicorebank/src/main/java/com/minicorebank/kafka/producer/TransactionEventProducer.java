package com.minicorebank.kafka.producer;

import com.minicorebank.kafka.event.TransactionCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionEventProducer {

    private final KafkaTemplate<String, TransactionCreatedEvent> kafkaTemplate;

    private static final String TOPIC = "transaction-created";

    public void send(TransactionCreatedEvent event) {
        kafkaTemplate.send(TOPIC, event.getUserId(), event);
    }
}
