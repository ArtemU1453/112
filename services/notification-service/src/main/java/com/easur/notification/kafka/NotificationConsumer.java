package com.easur.notification.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    @KafkaListener(topics = "notifications", groupId = "notification-service")
    public void onMessage(String message) {
        System.out.println("Received notification event: " + message);
        // TODO: enrich and deliver to external providers
    }
}
