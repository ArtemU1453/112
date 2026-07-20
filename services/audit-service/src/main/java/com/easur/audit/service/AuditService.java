package com.easur.audit.service;

import com.easur.audit.model.AuditLogEntity;
import com.easur.audit.repository.AuditLogRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AuditService {

    private final AuditLogRepository repository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public AuditService(AuditLogRepository repository, KafkaTemplate<String, String> kafkaTemplate) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public AuditLogEntity record(AuditLogEntity e) {
        AuditLogEntity saved = repository.save(e);
        kafkaTemplate.send("audit-logs", String.valueOf(saved.getId()));
        return saved;
    }
}
