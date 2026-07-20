package by.mchs.e112.audit.kafka;

import by.mchs.e112.audit.service.AuditService;
import java.time.Instant;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Единый потребитель топика audit.events — принимает события всех сервисов и записывает журнал.
 */
@Component
public class AuditEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(AuditEventConsumer.class);

    private final AuditService auditService;

    public AuditEventConsumer(AuditService auditService) {
        this.auditService = auditService;
    }

    @SuppressWarnings("unchecked")
    @KafkaListener(topics = "audit.events", groupId = "audit-service")
    public void onAuditEvent(Map<String, Object> event) {
        try {
            String service = String.valueOf(event.getOrDefault("service", "unknown"));
            String action = String.valueOf(event.getOrDefault("action", "UNKNOWN"));
            String entityType = String.valueOf(event.getOrDefault("entityType", "UNKNOWN"));
            String entityId = String.valueOf(event.getOrDefault("entityId", "-"));
            String actor = String.valueOf(event.getOrDefault("actor", "system"));
            Map<String, Object> details = event.get("details") instanceof Map<?, ?> map
                ? (Map<String, Object>) map : Map.of();
            Instant occurredAt = event.get("occurredAt") != null
                ? Instant.parse(event.get("occurredAt").toString()) : Instant.now();
            auditService.record(service, action, entityType, entityId, actor, details, occurredAt);
        } catch (Exception ex) {
            log.error("Не удалось записать аудит-событие: {}", event, ex);
        }
    }
}
