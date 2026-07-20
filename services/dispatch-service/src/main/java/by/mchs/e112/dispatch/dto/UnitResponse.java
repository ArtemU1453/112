package by.mchs.e112.dispatch.dto;

import java.time.Instant;
import java.util.UUID;

public record UnitResponse(
    UUID id,
    String callSign,
    String type,
    String status,
    UUID stationId,
    String stationName,
    int crewSize,
    double baseLatitude,
    double baseLongitude,
    Double currentLatitude,
    Double currentLongitude,
    UUID activeIncidentId,
    Instant updatedAt
) {
}
