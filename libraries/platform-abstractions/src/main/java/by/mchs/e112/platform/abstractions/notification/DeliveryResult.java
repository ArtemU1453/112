package by.mchs.e112.platform.abstractions.notification;

/**
 * Результат доставки уведомления.
 *
 * @param accepted   принято каналом к отправке
 * @param providerId идентификатор во внешней системе (если есть)
 * @param error      описание ошибки (если не принято)
 */
public record DeliveryResult(boolean accepted, String providerId, String error) {
    public static DeliveryResult ok(String providerId) {
        return new DeliveryResult(true, providerId, null);
    }

    public static DeliveryResult failed(String error) {
        return new DeliveryResult(false, null, error);
    }
}
