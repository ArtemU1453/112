package com.easur.realtime.service;

import com.easur.realtime.model.RealtimeEventEntity;
import com.easur.realtime.repository.RealtimeEventRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class RealtimeEventService {

    private final RealtimeEventRepository repository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public RealtimeEventService(RealtimeEventRepository repository, KafkaTemplate<String, String> kafkaTemplate) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public RealtimeEventEntity handleEvent(RealtimeEventEntity e) {
        RealtimeEventEntity saved = repository.save(e);
        kafkaTemplate.send("realtime-events", String.valueOf(saved.getId()));
        return saved;
    }
}
