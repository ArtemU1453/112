package com.easur.dispatch.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class DispatchTaskEntityConsumer {
    @KafkaListener(topics = "dispatch.events", groupId = "dispatch-service")
    public void consume(String message) { System.out.println("Consumed: " + message); }
}
