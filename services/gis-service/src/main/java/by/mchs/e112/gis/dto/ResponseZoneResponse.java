package by.mchs.e112.gis.dto;

import java.util.UUID;

public record ResponseZoneResponse(
    UUID zoneId,
    String name,
    UUID stationId,
    String serviceType
) {
}
