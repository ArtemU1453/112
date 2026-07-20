package com.easur.incident.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class IncidentEntityProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public IncidentEntityProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(String message) {
        kafkaTemplate.send("incident.events", message);
    }
}
