package by.mchs.e112.platform.abstractions.search;

import by.mchs.e112.platform.commons.dto.PageRequest;
import java.util.Map;

/**
 * Универсальный поисковый запрос (Search Platform). Комбинирует полнотекстовый запрос, фильтры по
 * атрибутам и пагинацию/сортировку (в составе {@link PageRequest}).
 *
 * @param index   логическое имя индекса/коллекции
 * @param text    полнотекстовый запрос (может быть пустым)
 * @param filters фильтры по атрибутам (поле → значение)
 * @param page    пагинация и сортировка
 */
public record SearchQuery(String index, String text, Map<String, Object> filters, PageRequest page) {
    public SearchQuery {
        filters = filters == null ? Map.of() : Map.copyOf(filters);
    }
}
