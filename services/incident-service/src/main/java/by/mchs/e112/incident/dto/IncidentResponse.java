package by.mchs.e112.incident.dto;

import java.time.Instant;
import java.util.UUID;

public record IncidentResponse(
    UUID id,
    String number,
    String type,
    String priority,
    String status,
    String description,
    String address,
    Double latitude,
    Double longitude,
    String callerPhone,
    String callerName,
    int casualtiesCount,
    UUID callId,
    String createdBy,
    String closedReason,
    Instant createdAt,
    Instant updatedAt
) {
}
