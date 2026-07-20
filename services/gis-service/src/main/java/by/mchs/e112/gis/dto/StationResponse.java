package by.mchs.e112.gis.dto;

import java.util.UUID;

public record StationResponse(
    UUID id,
    String name,
    String serviceType,
    String address,
    double latitude,
    double longitude,
    String phone
) {
}
