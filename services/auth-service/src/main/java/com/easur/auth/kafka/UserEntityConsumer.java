package com.easur.auth.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserEntityConsumer {
    @KafkaListener(topics = "auth.events", groupId = "auth-service")
    public void consume(String message) {
        System.out.println("Consumed: " + message);
    }
}
