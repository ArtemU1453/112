package by.mchs.e112.auth.kafka;

import java.time.Instant;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Публикация событий аудита в топик audit.events.
 */
@Component
public class AuditEventPublisher {

    public static final String TOPIC_AUDIT = "audit.events";

    private static final Logger log = LoggerFactory.getLogger(AuditEventPublisher.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public AuditEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(String action, String entityId, String actor, Map<String, Object> details) {
        Map<String, Object> event = Map.of(
            "service", "auth-service",
            "action", action,
            "entityType", "USER",
            "entityId", entityId,
            "actor", actor,
            "details", details,
            "occurredAt", Instant.now().toString()
        );
        kafkaTemplate.send(TOPIC_AUDIT, entityId, event)
            .whenComplete((result, ex) -> {
                if (ex != null) {
                    log.error("Не удалось отправить аудит-событие {} для {}", action, entityId, ex);
                }
            });
    }
}
