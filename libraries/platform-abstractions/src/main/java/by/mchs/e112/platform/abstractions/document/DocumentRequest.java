package by.mchs.e112.platform.abstractions.document;

import java.util.Map;

/**
 * Запрос генерации документа по шаблону (Document Platform / Document Lifecycle Management Standard).
 *
 * @param templateId идентификатор шаблона
 * @param format     формат вывода
 * @param model      данные для подстановки (без бизнес-логики — только модель представления)
 */
public record DocumentRequest(String templateId, DocumentFormat format, Map<String, Object> model) {
    public DocumentRequest {
        model = model == null ? Map.of() : Map.copyOf(model);
    }
}
