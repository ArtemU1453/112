package by.mchs.e112.platform.commons.error;

/**
 * Нарушение валидации отдельного поля (Validation/Error Handling Framework).
 *
 * @param field      имя поля
 * @param messageKey ключ локализуемого сообщения (Localization Platform)
 * @param message    человекочитаемое сообщение (уже локализованное или дефолтное)
 */
public record FieldViolation(String field, String messageKey, String message) {
}
