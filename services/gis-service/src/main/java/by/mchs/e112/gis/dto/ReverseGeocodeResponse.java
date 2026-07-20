package by.mchs.e112.gis.dto;

public record ReverseGeocodeResponse(
    String address,
    double latitude,
    double longitude,
    double distanceMeters
) {
}
