package by.mchs.e112.platform.commons.dto;

import java.util.List;

/**
 * Запрос страницы (API Guidelines: пагинация/сортировка). Нумерация страниц с нуля.
 *
 * @param page  номер страницы (>= 0)
 * @param size  размер страницы (1..maxSize)
 * @param sorts список сортировок (может быть пустым)
 */
public record PageRequest(int page, int size, List<Sort> sorts) {

    public static final int DEFAULT_SIZE = 20;
    public static final int MAX_SIZE = 200;

    public PageRequest {
        if (page < 0) {
            throw new IllegalArgumentException("page must be >= 0");
        }
        if (size < 1 || size > MAX_SIZE) {
            throw new IllegalArgumentException("size must be in [1, " + MAX_SIZE + "]");
        }
        sorts = sorts == null ? List.of() : List.copyOf(sorts);
    }

    public static PageRequest of(int page, int size) {
        return new PageRequest(page, size, List.of());
    }

    public static PageRequest firstPage() {
        return new PageRequest(0, DEFAULT_SIZE, List.of());
    }

    public long offset() {
        return (long) page * size;
    }
}
