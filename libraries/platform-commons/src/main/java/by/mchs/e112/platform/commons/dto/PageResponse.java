package by.mchs.e112.platform.commons.dto;

import java.util.List;

/**
 * Страница результатов (API Guidelines: метаданные пагинации).
 *
 * @param content       элементы страницы
 * @param page          номер страницы
 * @param size          размер страницы
 * @param totalElements всего элементов
 * @param <T>           тип элемента
 */
public record PageResponse<T>(List<T> content, int page, int size, long totalElements) {

    public PageResponse {
        content = content == null ? List.of() : List.copyOf(content);
    }

    public int totalPages() {
        return size == 0 ? 0 : (int) Math.ceil((double) totalElements / size);
    }

    public boolean hasNext() {
        return (long) (page + 1) * size < totalElements;
    }

    public static <T> PageResponse<T> of(List<T> content, PageRequest request, long total) {
        return new PageResponse<>(content, request.page(), request.size(), total);
    }
}
