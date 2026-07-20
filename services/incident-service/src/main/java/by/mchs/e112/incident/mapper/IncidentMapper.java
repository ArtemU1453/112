package by.mchs.e112.incident.mapper;

import by.mchs.e112.incident.domain.Incident;
import by.mchs.e112.incident.domain.IncidentHistoryEntry;
import by.mchs.e112.incident.dto.IncidentHistoryResponse;
import by.mchs.e112.incident.dto.IncidentResponse;
import org.springframework.stereotype.Component;

@Component
public class IncidentMapper {

    public IncidentResponse toResponse(Incident incident) {
        return new IncidentResponse(
            incident.getId(),
            incident.getNumber(),
            incident.getType().name(),
            incident.getPriority().name(),
            incident.getStatus().name(),
            incident.getDescription(),
            incident.getAddress(),
            incident.getLatitude(),
            incident.getLongitude(),
            incident.getCallerPhone(),
            incident.getCallerName(),
            incident.getCasualtiesCount(),
            incident.getCallId(),
            incident.getCreatedBy(),
            incident.getClosedReason(),
            incident.getCreatedAt(),
            incident.getUpdatedAt()
        );
    }

    public IncidentHistoryResponse toHistoryResponse(IncidentHistoryEntry entry) {
        return new IncidentHistoryResponse(
            entry.getAction(),
            entry.getActor(),
            entry.getComment(),
            entry.getOccurredAt()
        );
    }
}
