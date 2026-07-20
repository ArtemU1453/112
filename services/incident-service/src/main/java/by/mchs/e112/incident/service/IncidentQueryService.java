package by.mchs.e112.incident.service;

import by.mchs.e112.incident.domain.Incident;
import by.mchs.e112.incident.domain.IncidentStatus;
import by.mchs.e112.incident.dto.IncidentFilter;
import by.mchs.e112.incident.dto.IncidentHistoryResponse;
import by.mchs.e112.incident.dto.IncidentResponse;
import by.mchs.e112.incident.dto.IncidentStatsResponse;
import by.mchs.e112.incident.exception.IncidentNotFoundException;
import by.mchs.e112.incident.mapper.IncidentMapper;
import by.mchs.e112.incident.repository.IncidentRepository;
import by.mchs.e112.incident.repository.IncidentSpecifications;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Читающая сторона CQRS: выборки, фильтрация, статистика (кэш Redis).
 */
@Service
@Transactional(readOnly = true)
public class IncidentQueryService {

    private static final ZoneId ZONE_MINSK = ZoneId.of("Europe/Minsk");
    private static final List<IncidentStatus> ACTIVE_STATUSES = List.of(
        IncidentStatus.RECEIVED, IncidentStatus.CLASSIFIED,
        IncidentStatus.DISPATCHED, IncidentStatus.IN_PROGRESS);

    private final IncidentRepository repository;
    private final IncidentMapper mapper;

    public IncidentQueryService(IncidentRepository repository, IncidentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public IncidentResponse getById(UUID id) {
        return mapper.toResponse(find(id));
    }

    public Page<IncidentResponse> search(IncidentFilter filter, Pageable pageable) {
        return repository.findAll(IncidentSpecifications.byFilter(filter), pageable)
            .map(mapper::toResponse);
    }

    public List<IncidentResponse> getActive() {
        return repository.findByStatusIn(ACTIVE_STATUSES).stream()
            .map(mapper::toResponse)
            .toList();
    }

    public List<IncidentHistoryResponse> getHistory(UUID id) {
        return find(id).getHistory().stream()
            .map(mapper::toHistoryResponse)
            .toList();
    }

    @Cacheable(cacheNames = "incident-stats", key = "'summary'")
    public IncidentStatsResponse getStats() {
        Instant startOfDay = LocalDate.now(ZONE_MINSK).atStartOfDay(ZONE_MINSK).toInstant();
        Map<String, Long> byStatus = toMap(repository.countGroupByStatus());
        Map<String, Long> byType = toMap(repository.countGroupByType(startOfDay));
        Map<String, Long> byPriority = toMap(repository.countGroupByPriority(startOfDay));
        return new IncidentStatsResponse(
            repository.countByStatusIn(ACTIVE_STATUSES),
            repository.countByCreatedAtAfter(startOfDay),
            byStatus, byType, byPriority);
    }

    private Map<String, Long> toMap(List<IncidentRepository.CountByKey> rows) {
        Map<String, Long> result = new LinkedHashMap<>();
        for (IncidentRepository.CountByKey row : rows) {
            result.put(String.valueOf(row.getK()), row.getV());
        }
        return result;
    }

    private Incident find(UUID id) {
        return repository.findById(id).orElseThrow(() -> new IncidentNotFoundException(id));
    }
}
