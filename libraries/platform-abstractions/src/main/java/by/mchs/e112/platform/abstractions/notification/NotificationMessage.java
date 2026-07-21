package by.mchs.e112.platform.abstractions.notification;

import java.util.Map;

/**
 * Сообщение уведомления, независимое от канала (Notification Platform).
 *
 * @param channel    канал доставки
 * @param recipient  адрес получателя (в терминах канала)
 * @param subject    заголовок (при поддержке каналом)
 * @param body       тело (может быть шаблонизировано)
 * @param attributes дополнительные атрибуты канала/шаблона
 */
public record NotificationMessage(ChannelType channel,
                                  String recipient,
                                  String subject,
                                  String body,
                                  Map<String, String> attributes) {
    public NotificationMessage {
        attributes = attributes == null ? Map.of() : Map.copyOf(attributes);
    }
}
