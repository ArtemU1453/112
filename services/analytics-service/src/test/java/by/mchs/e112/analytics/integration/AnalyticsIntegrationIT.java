package by.mchs.e112.analytics.integration;

import static org.assertj.core.api.Assertions.assertThat;

import by.mchs.e112.analytics.dto.CategoryCount;
import by.mchs.e112.analytics.dto.Granularity;
import by.mchs.e112.analytics.dto.IncidentReportRow;
import by.mchs.e112.analytics.dto.KpiSummary;
import by.mchs.e112.analytics.dto.TimeBucketCount;
import by.mchs.e112.analytics.service.AnalyticsIngestionService;
import by.mchs.e112.analytics.service.AnalyticsQueryService;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
class AnalyticsIntegrationIT {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres =
        new PostgreSQLContainer<>("postgres:17-alpine")
            .withDatabaseName("analytics_db").withUsername("e112").withPassword("e112secret");

    @Autowired
    private AnalyticsIngestionService ingestionService;

    @Autowired
    private AnalyticsQueryService queryService;

    @Test
    void ingestsEventsIdempotentlyAndComputesAnalytics() {
        Instant base = Instant.parse("2026-07-20T08:00:00Z");
        UUID incidentA = UUID.randomUUID();
        UUID incidentB = UUID.randomUUID();
        UUID assignmentA1 = UUID.randomUUID();
        UUID assignmentA2 = UUID.randomUUID();

        // Происшествие A: создано, назначен наряд, решено.
        ingestionService.ingestIncidentEvent(incidentEvent(incidentA, "112-A", "FIRE", "HIGH",
            "RECEIVED", 2, base));
        ingestionService.ingestIncidentEvent(incidentEvent(incidentA, "112-A", "FIRE", "HIGH",
            "DISPATCHED", 2, base.plus(5, ChronoUnit.MINUTES)));
        ingestionService.ingestIncidentEvent(incidentEvent(incidentA, "112-A", "FIRE", "HIGH",
            "RESOLVED", 2, base.plus(30, ChronoUnit.MINUTES)));

        // Происшествие B: только создано.
        ingestionService.ingestIncidentEvent(incidentEvent(incidentB, "112-B", "MEDICAL", "MEDIUM",
            "RECEIVED", 0, base.plus(1, ChronoUnit.MINUTES)));

        // Два наряда на A (первый — на 3-й минуте), плюс повторная доставка первого (идемпотентность).
        ingestionService.ingestDispatchAssigned(dispatchEvent(assignmentA1, incidentA, "АЦ-1", 2.5,
            base.plus(3, ChronoUnit.MINUTES)));
        ingestionService.ingestDispatchAssigned(dispatchEvent(assignmentA2, incidentA, "АЦ-2", 4.0,
            base.plus(4, ChronoUnit.MINUTES)));
        ingestionService.ingestDispatchAssigned(dispatchEvent(assignmentA1, incidentA, "АЦ-1", 2.5,
            base.plus(3, ChronoUnit.MINUTES)));

        // Устаревшее событие (более раннее время) не должно откатывать статус A.
        ingestionService.ingestIncidentEvent(incidentEvent(incidentA, "112-A", "FIRE", "HIGH",
            "CANCELLED", 2, base.plus(1, ChronoUnit.MINUTES)));

        Instant from = base.minus(1, ChronoUnit.HOURS);
        Instant to = base.plus(1, ChronoUnit.DAYS);

        KpiSummary kpi = queryService.kpiSummary(from, to);
        assertThat(kpi.totalIncidents()).isEqualTo(2);
        assertThat(kpi.dispatchedIncidents()).isEqualTo(1);
        assertThat(kpi.dispatchRate()).isEqualTo(0.5);
        assertThat(kpi.resolvedIncidents()).isEqualTo(1);
        assertThat(kpi.resolutionRate()).isEqualTo(0.5);
        // Первый наряд A через 180 секунд после регистрации.
        assertThat(kpi.avgFirstDispatchSeconds()).isEqualTo(180.0);
        assertThat(kpi.byType()).extracting(CategoryCount::category)
            .containsExactlyInAnyOrder("FIRE", "MEDICAL");

        List<CategoryCount> byType = queryService.incidentsByType(from, to);
        assertThat(byType).allSatisfy(c -> assertThat(c.count()).isEqualTo(1));

        List<TimeBucketCount> daily = queryService.timeSeries(Granularity.DAY, from, to);
        assertThat(daily).hasSize(1);
        assertThat(daily.get(0).count()).isEqualTo(2);

        Page<IncidentReportRow> report = queryService.report(from, to, null, null, null,
            PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "createdAt")));
        assertThat(report.getTotalElements()).isEqualTo(2);

        IncidentReportRow rowA = report.getContent().stream()
            .filter(r -> r.incidentId().equals(incidentA)).findFirst().orElseThrow();
        assertThat(rowA.status()).isEqualTo("RESOLVED"); // устаревший CANCELLED проигнорирован
        assertThat(rowA.dispatchCount()).isEqualTo(2);    // повторный наряд не задвоился
        assertThat(rowA.firstDispatchAt()).isEqualTo(base.plus(3, ChronoUnit.MINUTES));

        IncidentReportRow rowB = report.getContent().stream()
            .filter(r -> r.incidentId().equals(incidentB)).findFirst().orElseThrow();
        assertThat(rowB.dispatchCount()).isZero();
        assertThat(rowB.firstDispatchAt()).isNull();

        Page<IncidentReportRow> onlyFire = queryService.report(from, to, "FIRE", null, null,
            PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "createdAt")));
        assertThat(onlyFire.getTotalElements()).isEqualTo(1);
        assertThat(onlyFire.getContent().get(0).incidentId()).isEqualTo(incidentA);
    }

    private Map<String, Object> incidentEvent(UUID id, String number, String type, String priority,
                                              String status, int casualties, Instant occurredAt) {
        return Map.of(
            "incidentId", id.toString(),
            "number", number,
            "type", type,
            "priority", priority,
            "status", status,
            "latitude", 53.9,
            "longitude", 27.56,
            "casualtiesCount", casualties,
            "occurredAt", occurredAt.toString());
    }

    private Map<String, Object> dispatchEvent(UUID assignmentId, UUID incidentId, String callSign,
                                              double distanceKm, Instant occurredAt) {
        return Map.of(
            "assignmentId", assignmentId.toString(),
            "incidentId", incidentId.toString(),
            "unitId", UUID.randomUUID().toString(),
            "unitCallSign", callSign,
            "distanceKm", distanceKm,
            "occurredAt", occurredAt.toString());
    }
}
