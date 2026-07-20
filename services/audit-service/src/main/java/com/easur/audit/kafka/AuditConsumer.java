package com.easur.audit.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AuditConsumer {

    @KafkaListener(topics = "audit-logs", groupId = "audit-service")
    public void onMessage(String message) {
        System.out.println("Audit log consumed: " + message);
    }
}
