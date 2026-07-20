package by.mchs.e112.gis.dto;

import java.util.UUID;

public record NearestStationResponse(
    UUID stationId,
    String name,
    String serviceType,
    String address,
    double latitude,
    double longitude,
    double distanceKm
) {
}
