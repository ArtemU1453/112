package by.mchs.e112.notification.dto;

import by.mchs.e112.notification.domain.NotificationChannel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NotificationRequest(
    @NotNull NotificationChannel channel,
    @NotBlank @Size(max = 255) String recipient,
    @Size(max = 255) String subject,
    @NotBlank @Size(max = 2000) String body,
    @Size(max = 64) String relatedEntityId
) {
}
