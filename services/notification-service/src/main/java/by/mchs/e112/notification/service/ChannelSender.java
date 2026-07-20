package by.mchs.e112.notification.service;

import by.mchs.e112.notification.domain.Notification;
import by.mchs.e112.notification.domain.NotificationChannel;

/**
 * Порт отправки уведомления по конкретному каналу (гексагональная архитектура).
 */
public interface ChannelSender {

    NotificationChannel channel();

    void send(Notification notification) throws Exception;
}
