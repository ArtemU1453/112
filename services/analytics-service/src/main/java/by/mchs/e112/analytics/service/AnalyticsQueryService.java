package by.mchs.e112.analytics.service;

import by.mchs.e112.analytics.domain.AnalyticsIncident;
import by.mchs.e112.analytics.dto.CategoryCount;
import by.mchs.e112.analytics.dto.Granularity;
import by.mchs.e112.analytics.dto.IncidentReportRow;
import by.mchs.e112.analytics.dto.KpiSummary;
import by.mchs.e112.analytics.dto.TimeBucketCount;
import by.mchs.e112.analytics.kafka.EventValues;
import by.mchs.e112.analytics.repository.AnalyticsDispatchRepository;
import by.mchs.e112.analytics.repository.AnalyticsIncidentRepository;
import by.mchs.e112.analytics.repository.projection.CategoryCountProjection;
import by.mchs.e112.analytics.repository.projection.DispatchAggregateProjection;
import jakarta.persistence.criteria.Predicate;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Запросы к аналитическим витринам: KPI, операционные срезы, временные ряды и табличные отчёты.
 * Только чтение — сервис не изменяет данные и не влияет на оперативный контур.
 */
@Service
@Transactional(readOnly = true)
public class AnalyticsQueryService {

    private static final List<String> RESOLVED_STATUSES = List.of("RESOLVED", "CLOSED");

    private final AnalyticsIncidentRepository incidentRepository;
    private final AnalyticsDispatchRepository dispatchRepository;

    public AnalyticsQueryService(AnalyticsIncidentRepository incidentRepository,
                                 AnalyticsDispatchRepository dispatchRepository) {
        this.incidentRepository = incidentRepository;
        this.dispatchRepository = dispatchRepository;
    }

    public KpiSummary kpiSummary(Instant from, Instant to) {
        long total = incidentRepository
            .countByCreatedAtGreaterThanEqualAndCreatedAtLessThan(from, to);
        long dispatched = incidentRepository.countDispatchedIncidents(from, to);
        long resolved = incidentRepository
            .countByStatusInAndCreatedAtGreaterThanEqualAndCreatedAtLessThan(
                RESOLVED_STATUSES, from, to);
        Double avgFirstDispatch = incidentRepository.avgFirstDispatchSeconds(from, to);

        double dispatchRate = total == 0 ? 0.0 : round4((double) dispatched / total);
        double resolutionRate = total == 0 ? 0.0 : round4((double) resolved / total);
        Double avgRounded = avgFirstDispatch == null ? null : round1(avgFirstDispatch);

        return new KpiSummary(from, to, total, dispatched, dispatchRate, resolved, resolutionRate,
            avgRounded,
            toCategoryCounts(incidentRepository.countByType(from, to)),
            toCategoryCounts(incidentRepository.countByPriority(from, to)),
            toCategoryCounts(incidentRepository.countByStatus(from, to)));
    }

    public List<CategoryCount> incidentsByType(Instant from, Instant to) {
        return toCategoryCounts(incidentRepository.countByType(from, to));
    }

    public List<CategoryCount> incidentsByPriority(Instant from, Instant to) {
        return toCategoryCounts(incidentRepository.countByPriority(from, to));
    }

    public List<CategoryCount> incidentsByStatus(Instant from, Instant to) {
        return toCategoryCounts(incidentRepository.countByStatus(from, to));
    }

    public List<TimeBucketCount> timeSeries(Granularity granularity, Instant from, Instant to) {
        List<Object[]> rows = incidentRepository.timeSeries(granularity.pgUnit(), from, to);
        List<TimeBucketCount> result = new ArrayList<>(rows.size());
        for (Object[] row : rows) {
            Instant bucketStart = EventValues.toInstant(row[0], from);
            long count = row[1] instanceof Number number ? number.longValue() : 0L;
            result.add(new TimeBucketCount(bucketStart, count));
        }
        return result;
    }

    public Page<IncidentReportRow> report(Instant from, Instant to, String type, String priority,
                                          String status, Pageable pageable) {
        Specification<AnalyticsIncident> spec = buildSpecification(from, to, type, priority, status);
        Page<AnalyticsIncident> page = incidentRepository.findAll(spec, pageable);

        List<UUID> ids = page.getContent().stream()
            .map(AnalyticsIncident::getIncidentId).toList();
        Map<UUID, DispatchAggregateProjection> aggregates = ids.isEmpty()
            ? Map.of()
            : dispatchRepository.aggregateByIncidentIds(ids).stream()
                .collect(Collectors.toMap(DispatchAggregateProjection::getIncidentId,
                    Function.identity()));

        return page.map(incident -> {
            DispatchAggregateProjection aggregate = aggregates.get(incident.getIncidentId());
            return new IncidentReportRow(
                incident.getIncidentId(),
                incident.getNumber(),
                incident.getType(),
                incident.getPriority(),
                incident.getStatus(),
                incident.getCasualtiesCount(),
                incident.getCreatedAt(),
                aggregate == null ? null : aggregate.getFirstDispatchAt(),
                aggregate == null ? 0L : aggregate.getDispatchCount());
        });
    }

    private Specification<AnalyticsIncident> buildSpecification(Instant from, Instant to, String type,
                                                               String priority, String status) {
        return (root, criteriaQuery, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.greaterThanOrEqualTo(root.get("createdAt"), from));
            predicates.add(builder.lessThan(root.get("createdAt"), to));
            if (type != null && !type.isBlank()) {
                predicates.add(builder.equal(root.get("type"), type));
            }
            if (priority != null && !priority.isBlank()) {
                predicates.add(builder.equal(root.get("priority"), priority));
            }
            if (status != null && !status.isBlank()) {
                predicates.add(builder.equal(root.get("status"), status));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private List<CategoryCount> toCategoryCounts(List<CategoryCountProjection> projections) {
        return projections.stream()
            .map(projection -> new CategoryCount(projection.getCategory(), projection.getCount()))
            .toList();
    }

    private double round4(double value) {
        return Math.round(value * 10_000.0) / 10_000.0;
    }

    private double round1(double value) {
        return Math.round(value * 10.0) / 10.0;
    }
}
