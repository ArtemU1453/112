package by.mchs.e112.audit.domain;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import org.hibernate.annotations.Type;

/**
 * Неизменяемая запись журнала аудита. Агрегирует события всех сервисов из топика audit.events.
 */
@Entity
@Table(name = "audit_event")
public class AuditEvent {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "service", nullable = false, length = 50)
    private String service;

    @Column(name = "action", nullable = false, length = 60)
    private String action;

    @Column(name = "entity_type", nullable = false, length = 30)
    private String entityType;

    @Column(name = "entity_id", nullable = false, length = 64)
    private String entityId;

    @Column(name = "actor", nullable = false, length = 64)
    private String actor;

    @Type(JsonType.class)
    @Column(name = "details", columnDefinition = "jsonb")
    private Map<String, Object> details;

    @Column(name = "occurred_at", nullable = false)
    private Instant occurredAt;

    @Column(name = "recorded_at", nullable = false)
    private Instant recordedAt;

    protected AuditEvent() {
    }

    public AuditEvent(UUID id, String service, String action, String entityType, String entityId,
                      String actor, Map<String, Object> details, Instant occurredAt) {
        this.id = id;
        this.service = service;
        this.action = action;
        this.entityType = entityType;
        this.entityId = entityId;
        this.actor = actor;
        this.details = details;
        this.occurredAt = occurredAt;
        this.recordedAt = Instant.now();
    }

    public UUID getId() { return id; }
    public String getService() { return service; }
    public String getAction() { return action; }
    public String getEntityType() { return entityType; }
    public String getEntityId() { return entityId; }
    public String getActor() { return actor; }
    public Map<String, Object> getDetails() { return details; }
    public Instant getOccurredAt() { return occurredAt; }
    public Instant getRecordedAt() { return recordedAt; }
}
