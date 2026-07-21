package by.mchs.e112.analytics.dto;

import java.time.Instant;
import java.util.List;

/**
 * Сводка ключевых показателей за период [from, to).
 *
 * @param from                    начало периода (включительно)
 * @param to                      конец периода (исключительно)
 * @param totalIncidents          всего зарегистрировано происшествий
 * @param dispatchedIncidents     происшествий, по которым был хотя бы один наряд
 * @param dispatchRate            доля происшествий с нарядом (0..1)
 * @param resolvedIncidents       происшествий в статусе RESOLVED/CLOSED
 * @param resolutionRate          доля закрытых/решённых (0..1)
 * @param avgFirstDispatchSeconds среднее время до первого наряда, сек (null — нет назначений)
 * @param byType                  распределение по типам
 * @param byPriority              распределение по приоритетам
 * @param byStatus                распределение по статусам
 */
public record KpiSummary(
    Instant from,
    Instant to,
    long totalIncidents,
    long dispatchedIncidents,
    double dispatchRate,
    long resolvedIncidents,
    double resolutionRate,
    Double avgFirstDispatchSeconds,
    List<CategoryCount> byType,
    List<CategoryCount> byPriority,
    List<CategoryCount> byStatus
) {
}
