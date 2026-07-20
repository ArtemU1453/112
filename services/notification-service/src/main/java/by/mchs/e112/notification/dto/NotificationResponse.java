package by.mchs.e112.notification.dto;

import java.time.Instant;
import java.util.UUID;

public record NotificationResponse(
    UUID id,
    String channel,
    String recipient,
    String subject,
    String body,
    String status,
    String relatedEntityId,
    String errorMessage,
    Instant createdAt,
    Instant sentAt
) {
}
