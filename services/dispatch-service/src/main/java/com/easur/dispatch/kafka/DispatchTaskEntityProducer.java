package com.easur.dispatch.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class DispatchTaskEntityProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public DispatchTaskEntityProducer(KafkaTemplate<String, String> kafkaTemplate) { this.kafkaTemplate = kafkaTemplate; }

    public void publish(String message) { kafkaTemplate.send("dispatch.events", message); }
}
