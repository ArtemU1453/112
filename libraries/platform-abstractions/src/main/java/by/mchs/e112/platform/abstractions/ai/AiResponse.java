package by.mchs.e112.platform.abstractions.ai;

import java.util.Map;

/**
 * Обобщённый ответ ИИ-провайдера.
 *
 * @param output     основной результат (текст/структурированный JSON как строка)
 * @param confidence уверенность 0..1 (если применимо, иначе -1)
 * @param metadata   доп. сведения (провайдер, модель-агностичные метки)
 */
public record AiResponse(String output, double confidence, Map<String, Object> metadata) {
    public AiResponse {
        metadata = metadata == null ? Map.of() : Map.copyOf(metadata);
    }
}
