package com.easur.incident.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class IncidentEntityConsumer {
    @KafkaListener(topics = "incident.events", groupId = "incident-service")
    public void consume(String message) {
        System.out.println("Consumed: " + message);
    }
}
