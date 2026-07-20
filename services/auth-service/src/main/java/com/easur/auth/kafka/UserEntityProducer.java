package com.easur.auth.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserEntityProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public UserEntityProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(String message) {
        kafkaTemplate.send("auth.events", message);
    }
}
