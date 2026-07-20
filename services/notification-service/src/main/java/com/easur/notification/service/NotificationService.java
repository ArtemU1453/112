package com.easur.notification.service;

import com.easur.notification.model.NotificationEntity;
import com.easur.notification.repository.NotificationRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final NotificationRepository repository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public NotificationService(NotificationRepository repository, KafkaTemplate<String, String> kafkaTemplate) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public NotificationEntity sendNotification(NotificationEntity entity) {
        NotificationEntity saved = repository.save(entity);
        // publish to kafka topic
        kafkaTemplate.send("notifications", String.valueOf(saved.getId()));
        return saved;
    }
}
