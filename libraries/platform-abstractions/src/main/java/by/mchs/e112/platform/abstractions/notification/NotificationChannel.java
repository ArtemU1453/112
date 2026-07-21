package by.mchs.e112.platform.abstractions.notification;

/**
 * Порт канала уведомлений (Notification Platform, Stage 4). Конкретные каналы (Email/SMS/Push/…)
 * реализуются адаптерами (гексагональная архитектура) без привязки к поставщику.
 */
public interface NotificationChannel {

    ChannelType type();

    boolean supports(NotificationMessage message);

    DeliveryResult send(NotificationMessage message);
}
