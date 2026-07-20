package com.easur.realtime.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class RealtimeConsumer {

    @KafkaListener(topics = "realtime-events", groupId = "realtime-service")
    public void onMessage(String message) {
        System.out.println("Realtime event consumed: " + message);
    }
}
