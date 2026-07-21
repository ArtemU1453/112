package by.mchs.e112.analytics.web;

import by.mchs.e112.analytics.dto.CategoryCount;
import by.mchs.e112.analytics.dto.Granularity;
import by.mchs.e112.analytics.dto.IncidentReportRow;
import by.mchs.e112.analytics.dto.KpiSummary;
import by.mchs.e112.analytics.dto.TimeBucketCount;
import by.mchs.e112.analytics.service.AnalyticsQueryService;
import java.time.Instant;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST-интерфейс аналитических витрин (read-only): KPI, операционные срезы, временные ряды и
 * табличные отчёты. Периоды задаются как ISO-8601 Instant; [from, to) — from включительно,
 * to исключительно. Все эндпоинты требуют аутентификации (Keycloak JWT).
 */
@RestController
@RequestMapping("/api/v1/analytics")
public class AnalyticsController {

    private final AnalyticsQueryService queryService;

    public AnalyticsController(AnalyticsQueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping("/kpi/summary")
    public KpiSummary kpiSummary(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to) {
        validateRange(from, to);
        return queryService.kpiSummary(from, to);
    }

    @GetMapping("/operational/by-type")
    public List<CategoryCount> byType(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to) {
        validateRange(from, to);
        return queryService.incidentsByType(from, to);
    }

    @GetMapping("/operational/by-priority")
    public List<CategoryCount> byPriority(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to) {
        validateRange(from, to);
        return queryService.incidentsByPriority(from, to);
    }

    @GetMapping("/operational/by-status")
    public List<CategoryCount> byStatus(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to) {
        validateRange(from, to);
        return queryService.incidentsByStatus(from, to);
    }

    @GetMapping("/operational/timeseries")
    public List<TimeBucketCount> timeSeries(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to,
        @RequestParam(defaultValue = "DAY") Granularity granularity) {
        validateRange(from, to);
        return queryService.timeSeries(granularity, from, to);
    }

    @GetMapping("/reporting/incidents")
    public Page<IncidentReportRow> report(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to,
        @RequestParam(required = false) String type,
        @RequestParam(required = false) String priority,
        @RequestParam(required = false) String status,
        @PageableDefault(size = 50, sort = "createdAt", direction = Sort.Direction.DESC)
        Pageable pageable) {
        validateRange(from, to);
        return queryService.report(from, to, type, priority, status, pageable);
    }

    private void validateRange(Instant from, Instant to) {
        if (!from.isBefore(to)) {
            throw new IllegalArgumentException("Параметр from должен быть строго раньше to");
        }
    }
}
