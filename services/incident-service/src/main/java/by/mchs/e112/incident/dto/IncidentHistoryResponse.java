package by.mchs.e112.incident.dto;

import java.time.Instant;

public record IncidentHistoryResponse(
    String action,
    String actor,
    String comment,
    Instant occurredAt
) {
}
