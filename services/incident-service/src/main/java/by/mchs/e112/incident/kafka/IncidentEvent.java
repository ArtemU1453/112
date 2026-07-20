package by.mchs.e112.incident.kafka;

import java.time.Instant;
import java.util.UUID;

/**
 * Интеграционное событие карточки происшествия (топики incident.created / incident.updated).
 */
public record IncidentEvent(
    UUID incidentId,
    String number,
    String type,
    String priority,
    String status,
    String address,
    Double latitude,
    Double longitude,
    String description,
    int casualtiesCount,
    String actor,
    Instant occurredAt
) {
}
