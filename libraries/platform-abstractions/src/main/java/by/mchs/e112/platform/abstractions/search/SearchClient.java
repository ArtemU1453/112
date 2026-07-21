package by.mchs.e112.platform.abstractions.search;

import by.mchs.e112.platform.commons.dto.PageResponse;

/**
 * Абстракция слоя поиска (Search Platform, Stage 4): полнотекстовый поиск, фильтрация по атрибутам,
 * сортировка, пагинация. Конкретный поисковый движок не фиксируется (TAP, CAP-SEARCH-001) —
 * подключается адаптером.
 */
public interface SearchClient {

    void index(String index, String id, java.util.Map<String, Object> document);

    void delete(String index, String id);

    PageResponse<SearchHit> search(SearchQuery query);
}
