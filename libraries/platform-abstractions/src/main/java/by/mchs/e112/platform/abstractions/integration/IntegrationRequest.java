package by.mchs.e112.platform.abstractions.integration;

import java.util.Map;

/**
 * Запрос интеграции, независимый от транспорта (Integration Platform).
 *
 * @param operation логическая операция/маршрут
 * @param headers   заголовки/метаданные
 * @param payload   полезная нагрузка
 */
public record IntegrationRequest(String operation, Map<String, String> headers, Object payload) {
    public IntegrationRequest {
        headers = headers == null ? Map.of() : Map.copyOf(headers);
    }
}
