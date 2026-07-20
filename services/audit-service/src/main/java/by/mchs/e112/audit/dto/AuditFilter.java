package by.mchs.e112.audit.dto;

import java.time.Instant;

public record AuditFilter(
    String service,
    String action,
    String entityType,
    String entityId,
    String actor,
    Instant from,
    Instant to
) {
}
