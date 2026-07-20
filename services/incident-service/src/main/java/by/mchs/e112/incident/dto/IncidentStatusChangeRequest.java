package by.mchs.e112.incident.dto;

import by.mchs.e112.incident.domain.IncidentStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record IncidentStatusChangeRequest(
    @NotNull IncidentStatus status,
    @Size(max = 1000) String comment
) {
}
