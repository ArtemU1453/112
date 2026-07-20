package by.mchs.e112.gis.dto;

public record GeocodeResponse(
    String matchedAddress,
    double latitude,
    double longitude,
    double confidence
) {
}
