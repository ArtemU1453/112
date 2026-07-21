package by.mchs.e112.platform.abstractions.search;

import java.util.Map;

/**
 * Единичный результат поиска.
 *
 * @param id     идентификатор документа
 * @param score  релевантность
 * @param source поля документа
 */
public record SearchHit(String id, double score, Map<String, Object> source) {
    public SearchHit {
        source = source == null ? Map.of() : Map.copyOf(source);
    }
}
