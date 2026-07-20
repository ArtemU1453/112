package by.mchs.e112.dispatch.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

public record UnitLocationUpdateRequest(
    @DecimalMin("51.0") @DecimalMax("56.5") double latitude,
    @DecimalMin("23.0") @DecimalMax("33.0") double longitude
) {
}
