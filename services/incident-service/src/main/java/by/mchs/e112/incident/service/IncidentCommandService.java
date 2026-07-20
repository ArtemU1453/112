package by.mchs.e112.incident.service;

import by.mchs.e112.incident.domain.Incident;
import by.mchs.e112.incident.dto.IncidentClassifyRequest;
import by.mchs.e112.incident.dto.IncidentCreateRequest;
import by.mchs.e112.incident.dto.IncidentResponse;
import by.mchs.e112.incident.dto.IncidentStatusChangeRequest;
import by.mchs.e112.incident.dto.IncidentUpdateRequest;
import by.mchs.e112.incident.exception.IncidentNotFoundException;
import by.mchs.e112.incident.kafka.IncidentEventProducer;
import by.mchs.e112.incident.mapper.IncidentMapper;
import by.mchs.e112.incident.repository.IncidentRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Командная сторона CQRS: все мутации агрегата «Карточка происшествия».
 */
@Service
public class IncidentCommandService {

    private static final ZoneId ZONE_MINSK = ZoneId.of("Europe/Minsk");

    private final IncidentRepository repository;
    private final IncidentMapper mapper;
    private final IncidentEventProducer eventProducer;

    public IncidentCommandService(IncidentRepository repository,
                                  IncidentMapper mapper,
                                  IncidentEventProducer eventProducer) {
        this.repository = repository;
        this.mapper = mapper;
        this.eventProducer = eventProducer;
    }

    @Transactional
    @CacheEvict(cacheNames = "incident-stats", allEntries = true)
    public IncidentResponse create(IncidentCreateRequest request, String actor) {
        Incident incident = new Incident(UUID.randomUUID(), request.type(), request.priority(),
            request.description(), request.address(), request.latitude(), request.longitude(),
            request.callerPhone(), request.callerName(), request.casualtiesCount(),
            request.callId(), actor);
        incident = repository.saveAndFlush(incident);
        incident.assignNumber(LocalDate.now(ZONE_MINSK).getYear());
        eventProducer.publishCreated(incident, actor);
        return mapper.toResponse(incident);
    }

    @Transactional
    @CacheEvict(cacheNames = "incident-stats", allEntries = true)
    public IncidentResponse update(UUID id, IncidentUpdateRequest request, String actor) {
        Incident incident = find(id);
        incident.updateDetails(request.description(), request.address(), request.latitude(),
            request.longitude(), request.callerName(), request.casualtiesCount(), actor);
        eventProducer.publishUpdated(incident, actor);
        return mapper.toResponse(incident);
    }

    @Transactional
    @CacheEvict(cacheNames = "incident-stats", allEntries = true)
    public IncidentResponse classify(UUID id, IncidentClassifyRequest request, String actor) {
        Incident incident = find(id);
        incident.classify(request.type(), request.priority(), actor);
        eventProducer.publishUpdated(incident, actor);
        return mapper.toResponse(incident);
    }

    @Transactional
    @CacheEvict(cacheNames = "incident-stats", allEntries = true)
    public IncidentResponse changeStatus(UUID id, IncidentStatusChangeRequest request, String actor) {
        Incident incident = find(id);
        String comment = request.comment() == null ? "Статус изменён" : request.comment();
        switch (request.status()) {
            case CLOSED -> incident.close(actor, comment);
            case CANCELLED -> incident.cancel(actor, comment);
            default -> incident.changeStatus(request.status(), actor, comment);
        }
        eventProducer.publishUpdated(incident, actor);
        return mapper.toResponse(incident);
    }

    @Transactional
    @CacheEvict(cacheNames = "incident-stats", allEntries = true)
    public void markDispatched(UUID id, String actor, String unitInfo) {
        Incident incident = find(id);
        incident.markDispatched(actor, unitInfo);
        eventProducer.publishUpdated(incident, actor);
    }

    private Incident find(UUID id) {
        return repository.findById(id).orElseThrow(() -> new IncidentNotFoundException(id));
    }
}
