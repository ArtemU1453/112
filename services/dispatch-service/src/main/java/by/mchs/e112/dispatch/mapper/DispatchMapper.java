package by.mchs.e112.dispatch.mapper;

import by.mchs.e112.dispatch.domain.Assignment;
import by.mchs.e112.dispatch.domain.Unit;
import by.mchs.e112.dispatch.dto.AssignmentResponse;
import by.mchs.e112.dispatch.dto.UnitResponse;
import org.springframework.stereotype.Component;

@Component
public class DispatchMapper {

    public UnitResponse toResponse(Unit unit) {
        return new UnitResponse(
            unit.getId(),
            unit.getCallSign(),
            unit.getType().name(),
            unit.getStatus().name(),
            unit.getStationId(),
            unit.getStationName(),
            unit.getCrewSize(),
            unit.getBaseLatitude(),
            unit.getBaseLongitude(),
            unit.getCurrentLatitude(),
            unit.getCurrentLongitude(),
            unit.getActiveIncidentId(),
            unit.getUpdatedAt()
        );
    }

    public AssignmentResponse toResponse(Assignment assignment) {
        return new AssignmentResponse(
            assignment.getId(),
            assignment.getIncidentId(),
            assignment.getUnitId(),
            assignment.getUnitCallSign(),
            assignment.getStatus().name(),
            assignment.getAssignedBy(),
            assignment.getDistanceKm(),
            assignment.getAssignedAt(),
            assignment.getCompletedAt()
        );
    }
}
