package by.mchs.e112.analytics.service;

import by.mchs.e112.analytics.domain.AnalyticsDispatch;
import by.mchs.e112.analytics.domain.AnalyticsIncident;
import by.mchs.e112.analytics.kafka.EventValues;
import by.mchs.e112.analytics.repository.AnalyticsDispatchRepository;
import by.mchs.e112.analytics.repository.AnalyticsIncidentRepository;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Наполнение аналитических витрин из интеграционных событий. Все операции идемпотентны:
 * происшествия — upsert по incidentId с last-write-wins по времени события; назначения — вставка
 * по assignmentId (повторные события игнорируются). Витрины изолированы от источников истины домена.
 */
@Service
public class AnalyticsIngestionService {

    private static final Logger log = LoggerFactory.getLogger(AnalyticsIngestionService.class);
    private static final String STATUS_RESOLVED = "RESOLVED";
    private static final String STATUS_CLOSED = "CLOSED";

    private final AnalyticsIncidentRepository incidentRepository;
    private final AnalyticsDispatchRepository dispatchRepository;

    public AnalyticsIngestionService(AnalyticsIncidentRepository incidentRepository,
                                     AnalyticsDispatchRepository dispatchRepository) {
        this.incidentRepository = incidentRepository;
        this.dispatchRepository = dispatchRepository;
    }

    @Transactional
    public void ingestIncidentEvent(Map<String, Object> event) {
        UUID incidentId = EventValues.optUuid(event.get("incidentId"));
        if (incidentId == null) {
            log.warn("Событие происшествия без корректного incidentId отброшено: {}", event);
            return;
        }
        Instant occurredAt = EventValues.toInstant(event.get("occurredAt"), Instant.now());
        String type = EventValues.str(event, "type", "UNKNOWN");
        String priority = EventValues.str(event, "priority", "UNKNOWN");
        String status = EventValues.str(event, "status", "UNKNOWN");
        int casualties = EventValues.toInt(event.get("casualtiesCount"), 0);
        Double latitude = EventValues.toDoubleOrNull(event.get("latitude"));
        Double longitude = EventValues.toDoubleOrNull(event.get("longitude"));
        String number = EventValues.str(event, "number", null);

        Optional<AnalyticsIncident> existing = incidentRepository.findById(incidentId);
        if (existing.isEmpty()) {
            AnalyticsIncident incident = new AnalyticsIncident(incidentId, number, type, priority,
                status, casualties, latitude, longitude, occurredAt);
            applyStatusTimestamps(incident, status, occurredAt);
            incidentRepository.save(incident);
            return;
        }

        AnalyticsIncident incident = existing.get();
        Instant lastKnown = incident.getUpdatedAt() != null
            ? incident.getUpdatedAt() : incident.getCreatedAt();
        if (occurredAt.isBefore(lastKnown)) {
            // Устаревшее событие пришло после более нового — изменяемые поля не трогаем.
            return;
        }
        incident.setType(type);
        incident.setPriority(priority);
        incident.setStatus(status);
        incident.setCasualtiesCount(casualties);
        if (latitude != null) {
            incident.setLatitude(latitude);
        }
        if (longitude != null) {
            incident.setLongitude(longitude);
        }
        if (number != null) {
            incident.setNumber(number);
        }
        incident.setUpdatedAt(occurredAt);
        applyStatusTimestamps(incident, status, occurredAt);
        incidentRepository.save(incident);
    }

    @Transactional
    public void ingestDispatchAssigned(Map<String, Object> event) {
        UUID assignmentId = EventValues.optUuid(event.get("assignmentId"));
        UUID incidentId = EventValues.optUuid(event.get("incidentId"));
        UUID unitId = EventValues.optUuid(event.get("unitId"));
        if (assignmentId == null || incidentId == null || unitId == null) {
            log.warn("Событие назначения с неполными идентификаторами отброшено: {}", event);
            return;
        }
        if (dispatchRepository.existsById(assignmentId)) {
            return;
        }
        String callSign = EventValues.str(event, "unitCallSign", null);
        Double distanceKm = EventValues.toDoubleOrNull(event.get("distanceKm"));
        Instant occurredAt = EventValues.toInstant(event.get("occurredAt"), Instant.now());
        dispatchRepository.save(new AnalyticsDispatch(
            assignmentId, incidentId, unitId, callSign, distanceKm, occurredAt));
    }

    private void applyStatusTimestamps(AnalyticsIncident incident, String status, Instant occurredAt) {
        if (STATUS_RESOLVED.equals(status) && incident.getResolvedAt() == null) {
            incident.setResolvedAt(occurredAt);
        }
        if (STATUS_CLOSED.equals(status)) {
            if (incident.getClosedAt() == null) {
                incident.setClosedAt(occurredAt);
            }
            if (incident.getResolvedAt() == null) {
                incident.setResolvedAt(occurredAt);
            }
        }
    }
}
