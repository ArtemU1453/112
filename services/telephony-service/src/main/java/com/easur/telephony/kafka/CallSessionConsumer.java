package com.easur.telephony.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CallSessionConsumer {
    @KafkaListener(topics = "telephony.events", groupId = "telephony-service")
    public void consume(String msg) { System.out.println("Consumed telephony: " + msg); }
}
