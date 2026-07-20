package by.mchs.e112.dispatch.kafka;

import by.mchs.e112.dispatch.domain.Assignment;
import by.mchs.e112.dispatch.domain.Unit;
import java.time.Instant;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class DispatchEventProducer {

    public static final String TOPIC_ASSIGNED = "dispatch.assigned";
    public static final String TOPIC_UNIT_STATUS = "unit.status-changed";
    public static final String TOPIC_AUDIT = "audit.events";

    private static final Logger log = LoggerFactory.getLogger(DispatchEventProducer.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public DispatchEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishAssigned(Assignment assignment, String actor) {
        Map<String, Object> event = Map.of(
            "assignmentId", assignment.getId().toString(),
            "incidentId", assignment.getIncidentId().toString(),
            "unitId", assignment.getUnitId().toString(),
            "unitCallSign", assignment.getUnitCallSign(),
            "distanceKm", assignment.getDistanceKm() == null ? 0.0 : assignment.getDistanceKm(),
            "actor", actor,
            "occurredAt", Instant.now().toString()
        );
        send(TOPIC_ASSIGNED, assignment.getIncidentId().toString(), event);
        audit("UNIT_ASSIGNED", assignment.getId().toString(), actor, event);
    }

    public void publishUnitStatus(Unit unit, String actor) {
        Map<String, Object> event = Map.of(
            "unitId", unit.getId().toString(),
            "callSign", unit.getCallSign(),
            "status", unit.getStatus().name(),
            "latitude", unit.getCurrentLatitude() == null ? 0.0 : unit.getCurrentLatitude(),
            "longitude", unit.getCurrentLongitude() == null ? 0.0 : unit.getCurrentLongitude(),
            "activeIncidentId", unit.getActiveIncidentId() == null ? "" : unit.getActiveIncidentId().toString(),
            "occurredAt", Instant.now().toString()
        );
        send(TOPIC_UNIT_STATUS, unit.getId().toString(), event);
        audit("UNIT_STATUS_CHANGED", unit.getId().toString(), actor, event);
    }

    private void audit(String action, String entityId, String actor, Map<String, Object> details) {
        Map<String, Object> event = Map.of(
            "service", "dispatch-service",
            "action", action,
            "entityType", "DISPATCH",
            "entityId", entityId,
            "actor", actor,
            "details", details,
            "occurredAt", Instant.now().toString()
        );
        send(TOPIC_AUDIT, entityId, event);
    }

    private void send(String topic, String key, Object payload) {
        kafkaTemplate.send(topic, key, payload).whenComplete((r, ex) -> {
            if (ex != null) {
                log.error("Ошибка публикации в {} ключ {}", topic, key, ex);
            }
        });
    }
}
