package by.mchs.e112.dispatch.dto;

import java.time.Instant;
import java.util.UUID;

public record AssignmentResponse(
    UUID id,
    UUID incidentId,
    UUID unitId,
    String unitCallSign,
    String status,
    String assignedBy,
    Double distanceKm,
    Instant assignedAt,
    Instant completedAt
) {
}
