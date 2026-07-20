package by.mchs.e112.dispatch.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record AssignmentRequest(
    @NotNull UUID incidentId,
    @NotNull UUID unitId
) {
}
