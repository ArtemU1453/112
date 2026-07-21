package by.mchs.e112.platform.abstractions.messaging;

import java.time.Instant;
import java.util.Map;

/**
 * Базовое сообщение шины (Messaging Platform). Общая оболочка для команд и событий с метаданными
 * трассировки и идемпотентности.
 *
 * @param id            идентификатор сообщения (ключ идемпотентности)
 * @param type          логический тип (топик/маршрут)
 * @param correlationId сквозной идентификатор
 * @param occurredAt    момент возникновения (UTC)
 * @param headers       заголовки
 * @param payload       полезная нагрузка (сериализуемая)
 */
public record Message(String id,
                      String type,
                      String correlationId,
                      Instant occurredAt,
                      Map<String, String> headers,
                      Object payload) {
    public Message {
        headers = headers == null ? Map.of() : Map.copyOf(headers);
    }
}
