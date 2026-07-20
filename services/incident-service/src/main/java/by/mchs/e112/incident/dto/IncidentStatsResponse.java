package by.mchs.e112.incident.dto;

import java.util.Map;

public record IncidentStatsResponse(
    long totalActive,
    long totalToday,
    Map<String, Long> byStatus,
    Map<String, Long> byType,
    Map<String, Long> byPriority
) {
}
