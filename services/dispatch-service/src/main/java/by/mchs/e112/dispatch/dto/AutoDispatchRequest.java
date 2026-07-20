package by.mchs.e112.dispatch.dto;

import by.mchs.e112.dispatch.domain.UnitType;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Автоматический подбор ближайшего свободного подразделения нужного типа.
 */
public record AutoDispatchRequest(
    @NotNull UUID incidentId,
    @NotNull UnitType requiredType,
    @DecimalMin("51.0") @DecimalMax("56.5") double latitude,
    @DecimalMin("23.0") @DecimalMax("33.0") double longitude
) {
}
