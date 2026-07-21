package by.mchs.e112.platform.abstractions.notification;

/** Типы каналов уведомлений (Notification Platform). Каналы подключаются через абстракции. */
public enum ChannelType {
    IN_APP,
    EMAIL,
    SMS,
    PUSH,
    SYSTEM_EVENT
}
