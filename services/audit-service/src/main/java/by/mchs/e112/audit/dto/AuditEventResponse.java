package by.mchs.e112.audit.dto;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public record AuditEventResponse(
    UUID id,
    String service,
    String action,
    String entityType,
    String entityId,
    String actor,
    Map<String, Object> details,
    Instant occurredAt,
    Instant recordedAt
) {
}
