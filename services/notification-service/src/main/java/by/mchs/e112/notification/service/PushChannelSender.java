package by.mchs.e112.notification.service;

import by.mchs.e112.notification.domain.Notification;
import by.mchs.e112.notification.domain.NotificationChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Push-уведомления мобильным приложениям экипажей. Интеграция с провайдером push
 * фиксирует событие в журнале; реальный провайдер подключается через конфигурацию.
 */
@Component
public class PushChannelSender implements ChannelSender {

    private static final Logger log = LoggerFactory.getLogger(PushChannelSender.class);

    @Override
    public NotificationChannel channel() {
        return NotificationChannel.PUSH;
    }

    @Override
    public void send(Notification notification) {
        log.info("PUSH → {}: {} / {}", notification.getRecipient(),
            notification.getSubject(), notification.getBody());
    }
}
