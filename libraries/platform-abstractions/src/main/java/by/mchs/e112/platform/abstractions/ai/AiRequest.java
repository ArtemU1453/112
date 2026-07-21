package by.mchs.e112.platform.abstractions.ai;

import java.util.Map;

/**
 * Обобщённый запрос к ИИ-провайдеру (AI Integration Framework / AI Provider Abstraction Standard).
 * Независим от конкретной модели/поставщика (запрет привязки к модели, Stage 4).
 *
 * @param input   входные данные (текст/ссылка на медиа/бинарный ключ)
 * @param options параметры выполнения (модель-агностичные)
 */
public record AiRequest(String input, Map<String, Object> options) {
    public AiRequest {
        options = options == null ? Map.of() : Map.copyOf(options);
    }
}
