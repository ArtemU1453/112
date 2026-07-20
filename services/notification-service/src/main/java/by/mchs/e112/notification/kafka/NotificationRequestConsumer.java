package by.mchs.e112.notification.kafka;

import by.mchs.e112.notification.domain.NotificationChannel;
import by.mchs.e112.notification.dto.NotificationRequest;
import by.mchs.e112.notification.service.NotificationService;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Потребитель notification.requested — прямые запросы на отправку уведомлений от сервисов.
 */
@Component
public class NotificationRequestConsumer {

    private static final Logger log = LoggerFactory.getLogger(NotificationRequestConsumer.class);

    private final NotificationService notificationService;

    public NotificationRequestConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "notification.requested", groupId = "notification-service")
    public void onNotificationRequested(Map<String, Object> event) {
        try {
            NotificationChannel channel = NotificationChannel.valueOf(
                String.valueOf(event.getOrDefault("channel", "EMAIL")));
            String recipient = String.valueOf(event.get("recipient"));
            String subject = event.get("subject") == null ? null : event.get("subject").toString();
            String body = String.valueOf(event.getOrDefault("body", ""));
            String related = event.get("relatedEntityId") == null
                ? null : event.get("relatedEntityId").toString();
            notificationService.send(new NotificationRequest(channel, recipient, subject, body, related));
        } catch (Exception ex) {
            log.error("Ошибка обработки notification.requested: {}", event, ex);
        }
    }
}
