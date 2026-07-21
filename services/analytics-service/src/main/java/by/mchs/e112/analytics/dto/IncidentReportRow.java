package by.mchs.e112.analytics.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * Строка табличного отчёта по происшествиям.
 *
 * @param firstDispatchAt время первого наряда (null — наряд не назначался)
 * @param dispatchCount   число назначенных нарядов
 */
public record IncidentReportRow(
    UUID incidentId,
    String number,
    String type,
    String priority,
    String status,
    int casualtiesCount,
    Instant createdAt,
    Instant firstDispatchAt,
    long dispatchCount
) {
}
