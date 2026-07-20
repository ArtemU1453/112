package com.easur.telephony.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CallSessionProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    public CallSessionProducer(KafkaTemplate<String, String> kafkaTemplate) { this.kafkaTemplate = kafkaTemplate; }
    public void publish(String msg) { kafkaTemplate.send("telephony.events", msg); }
}
