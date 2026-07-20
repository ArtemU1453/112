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
 * Потребитель dispatch.assigned — уведомляет экипаж назначенного подразделения (push).
 */
@Component
public class DispatchAssignedConsumer {

    private static final Logger log = LoggerFactory.getLogger(DispatchAssignedConsumer.class);

    private final NotificationService notificationService;

    public DispatchAssignedConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "dispatch.assigned", groupId = "notification-service")
    public void onDispatchAssigned(Map<String, Object> event) {
        try {
            String callSign = String.valueOf(event.getOrDefault("unitCallSign", "наряд"));
            String incidentId = String.valueOf(event.getOrDefault("incidentId", "-"));
            String body = "Ваше подразделение %s назначено на происшествие. Выезд немедленно."
                .formatted(callSign);
            notificationService.send(new NotificationRequest(NotificationChannel.PUSH,
                callSign, "Назначение на выезд", body, incidentId));
        } catch (Exception ex) {
            log.error("Ошибка обработки dispatch.assigned: {}", event, ex);
        }
    }
}
