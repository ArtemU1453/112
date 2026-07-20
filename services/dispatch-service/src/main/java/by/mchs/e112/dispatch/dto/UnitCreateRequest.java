package by.mchs.e112.dispatch.dto;

import by.mchs.e112.dispatch.domain.UnitType;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record UnitCreateRequest(
    @NotBlank @Size(max = 30) String callSign,
    @NotNull UnitType type,
    @NotNull UUID stationId,
    @NotBlank @Size(max = 200) String stationName,
    @Min(1) int crewSize,
    @DecimalMin("51.0") @DecimalMax("56.5") double baseLatitude,
    @DecimalMin("23.0") @DecimalMax("33.0") double baseLongitude
) {
}
