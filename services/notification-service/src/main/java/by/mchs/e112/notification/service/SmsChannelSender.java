package by.mchs.e112.notification.service;

import by.mchs.e112.notification.domain.Notification;
import by.mchs.e112.notification.domain.NotificationChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/**
 * Отправка SMS через HTTP-шлюз оператора. URL шлюза настраивается; при отсутствии
 * реального шлюза сообщение фиксируется в журнале (dry-run) без потери события.
 */
@Component
public class SmsChannelSender implements ChannelSender {

    private static final Logger log = LoggerFactory.getLogger(SmsChannelSender.class);

    private final RestClient restClient;
    private final String gatewayUrl;
    private final boolean enabled;

    public SmsChannelSender(@Value("${app.sms.gateway-url:}") String gatewayUrl,
                            @Value("${app.sms.enabled:false}") boolean enabled) {
        this.gatewayUrl = gatewayUrl;
        this.enabled = enabled;
        this.restClient = RestClient.create();
    }

    @Override
    public NotificationChannel channel() {
        return NotificationChannel.SMS;
    }

    @Override
    public void send(Notification notification) {
        if (!enabled || gatewayUrl.isBlank()) {
            log.info("SMS (dry-run) → {}: {}", notification.getRecipient(), notification.getBody());
            return;
        }
        restClient.post()
            .uri(gatewayUrl)
            .body(new SmsPayload(notification.getRecipient(), notification.getBody()))
            .retrieve()
            .toBodilessEntity();
    }

    private record SmsPayload(String phone, String text) {
    }
}
