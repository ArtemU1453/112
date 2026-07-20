package by.mchs.e112.incident.dto;

import by.mchs.e112.incident.domain.IncidentPriority;
import by.mchs.e112.incident.domain.IncidentType;
import jakarta.validation.constraints.NotNull;

public record IncidentClassifyRequest(
    @NotNull IncidentType type,
    @NotNull IncidentPriority priority
) {
}
