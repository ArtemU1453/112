package by.mchs.e112.platform.abstractions.integration;

import java.util.Map;

/**
 * Ответ интеграции.
 *
 * @param success  успешность
 * @param headers  заголовки/метаданные ответа
 * @param payload  полезная нагрузка
 */
public record IntegrationResponse(boolean success, Map<String, String> headers, Object payload) {
    public IntegrationResponse {
        headers = headers == null ? Map.of() : Map.copyOf(headers);
    }
}
