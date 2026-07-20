package by.mchs.e112.incident.dto;

import by.mchs.e112.incident.domain.IncidentPriority;
import by.mchs.e112.incident.domain.IncidentStatus;
import by.mchs.e112.incident.domain.IncidentType;
import java.time.Instant;

public record IncidentFilter(
    IncidentStatus status,
    IncidentType type,
    IncidentPriority priority,
    String addressContains,
    Instant createdFrom,
    Instant createdTo
) {
}
