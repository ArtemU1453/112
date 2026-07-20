package by.mchs.e112.dispatch.service;

import by.mchs.e112.dispatch.domain.Assignment;
import by.mchs.e112.dispatch.domain.AssignmentStatus;
import by.mchs.e112.dispatch.domain.GeoDistance;
import by.mchs.e112.dispatch.domain.Unit;
import by.mchs.e112.dispatch.domain.UnitStatus;
import by.mchs.e112.dispatch.dto.AssignmentRequest;
import by.mchs.e112.dispatch.dto.AssignmentResponse;
import by.mchs.e112.dispatch.dto.AutoDispatchRequest;
import by.mchs.e112.dispatch.dto.UnitCreateRequest;
import by.mchs.e112.dispatch.dto.UnitLocationUpdateRequest;
import by.mchs.e112.dispatch.dto.UnitResponse;
import by.mchs.e112.dispatch.dto.UnitStatusChangeRequest;
import by.mchs.e112.dispatch.exception.AssignmentNotFoundException;
import by.mchs.e112.dispatch.exception.ConflictException;
import by.mchs.e112.dispatch.exception.NoAvailableUnitException;
import by.mchs.e112.dispatch.exception.UnitNotAvailableException;
import by.mchs.e112.dispatch.exception.UnitNotFoundException;
import by.mchs.e112.dispatch.kafka.DispatchEventProducer;
import by.mchs.e112.dispatch.mapper.DispatchMapper;
import by.mchs.e112.dispatch.repository.AssignmentRepository;
import by.mchs.e112.dispatch.repository.UnitRepository;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DispatchService {

    private final UnitRepository unitRepository;
    private final AssignmentRepository assignmentRepository;
    private final DispatchMapper mapper;
    private final DispatchEventProducer eventProducer;

    public DispatchService(UnitRepository unitRepository,
                           AssignmentRepository assignmentRepository,
                           DispatchMapper mapper,
                           DispatchEventProducer eventProducer) {
        this.unitRepository = unitRepository;
        this.assignmentRepository = assignmentRepository;
        this.mapper = mapper;
        this.eventProducer = eventProducer;
    }

    @Transactional
    public UnitResponse createUnit(UnitCreateRequest request, String actor) {
        if (unitRepository.existsByCallSign(request.callSign())) {
            throw new ConflictException("Подразделение с позывным '%s' уже существует".formatted(request.callSign()));
        }
        Unit unit = new Unit(UUID.randomUUID(), request.callSign(), request.type(), request.stationId(),
            request.stationName(), request.crewSize(), request.baseLatitude(), request.baseLongitude());
        unitRepository.save(unit);
        eventProducer.publishUnitStatus(unit, actor);
        return mapper.toResponse(unit);
    }

    @Transactional(readOnly = true)
    public UnitResponse getUnit(UUID id) {
        return mapper.toResponse(findUnit(id));
    }

    @Transactional(readOnly = true)
    public List<UnitResponse> getAllUnits() {
        return unitRepository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<UnitResponse> getAvailableUnits() {
        return unitRepository.findByStatus(UnitStatus.AVAILABLE).stream().map(mapper::toResponse).toList();
    }

    @Transactional
    public UnitResponse changeUnitStatus(UUID id, UnitStatusChangeRequest request, String actor) {
        Unit unit = findUnit(id);
        unit.changeStatus(request.status());
        if (request.status() == UnitStatus.AVAILABLE) {
            completeActiveAssignments(unit.getId());
        }
        eventProducer.publishUnitStatus(unit, actor);
        return mapper.toResponse(unit);
    }

    @Transactional
    public UnitResponse updateUnitLocation(UUID id, UnitLocationUpdateRequest request, String actor) {
        Unit unit = findUnit(id);
        unit.updateLocation(request.latitude(), request.longitude());
        eventProducer.publishUnitStatus(unit, actor);
        return mapper.toResponse(unit);
    }

    @Transactional
    public AssignmentResponse assign(AssignmentRequest request, String actor) {
        Unit unit = findUnit(request.unitId());
        if (!unit.isAvailable()) {
            throw new UnitNotAvailableException(unit.getCallSign());
        }
        Assignment assignment = createAssignment(request.incidentId(), unit, actor, null);
        return mapper.toResponse(assignment);
    }

    @Transactional
    public AssignmentResponse autoDispatch(AutoDispatchRequest request, String actor) {
        List<Unit> candidates = unitRepository.findByTypeAndStatus(request.requiredType(), UnitStatus.AVAILABLE);
        Unit nearest = candidates.stream()
            .min(Comparator.comparingDouble(u -> distance(u, request.latitude(), request.longitude())))
            .orElseThrow(() -> new NoAvailableUnitException(request.requiredType().name()));
        double distanceKm = distance(nearest, request.latitude(), request.longitude());
        Assignment assignment = createAssignment(request.incidentId(), nearest, actor, distanceKm);
        return mapper.toResponse(assignment);
    }

    @Transactional
    public AssignmentResponse completeAssignment(UUID assignmentId, String actor) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
            .orElseThrow(() -> new AssignmentNotFoundException(assignmentId));
        assignment.complete();
        Unit unit = findUnit(assignment.getUnitId());
        unit.changeStatus(UnitStatus.RETURNING);
        eventProducer.publishUnitStatus(unit, actor);
        return mapper.toResponse(assignment);
    }

    @Transactional
    public AssignmentResponse recallAssignment(UUID assignmentId, String actor) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
            .orElseThrow(() -> new AssignmentNotFoundException(assignmentId));
        assignment.recall();
        Unit unit = findUnit(assignment.getUnitId());
        unit.changeStatus(UnitStatus.AVAILABLE);
        eventProducer.publishUnitStatus(unit, actor);
        return mapper.toResponse(assignment);
    }

    @Transactional(readOnly = true)
    public List<AssignmentResponse> getAssignmentsByIncident(UUID incidentId) {
        return assignmentRepository.findByIncidentId(incidentId).stream().map(mapper::toResponse).toList();
    }

    private Assignment createAssignment(UUID incidentId, Unit unit, String actor, Double distanceKm) {
        unit.dispatchTo(incidentId);
        Assignment assignment = new Assignment(UUID.randomUUID(), incidentId, unit, actor, distanceKm);
        assignmentRepository.save(assignment);
        eventProducer.publishAssigned(assignment, actor);
        eventProducer.publishUnitStatus(unit, actor);
        return assignment;
    }

    private void completeActiveAssignments(UUID unitId) {
        assignmentRepository.findByUnitIdAndStatus(unitId, AssignmentStatus.ACTIVE)
            .forEach(Assignment::complete);
    }

    private double distance(Unit unit, double lat, double lon) {
        double fromLat = unit.getCurrentLatitude() != null ? unit.getCurrentLatitude() : unit.getBaseLatitude();
        double fromLon = unit.getCurrentLongitude() != null ? unit.getCurrentLongitude() : unit.getBaseLongitude();
        return GeoDistance.haversineKm(fromLat, fromLon, lat, lon);
    }

    private Unit findUnit(UUID id) {
        return unitRepository.findById(id).orElseThrow(() -> new UnitNotFoundException(id));
    }
}
