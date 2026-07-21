package by.mchs.e112.platform.commons.dto;

/**
 * Параметр сортировки.
 *
 * @param field     поле сортировки
 * @param direction направление
 */
public record Sort(String field, SortDirection direction) {
    public static Sort asc(String field) {
        return new Sort(field, SortDirection.ASC);
    }

    public static Sort desc(String field) {
        return new Sort(field, SortDirection.DESC);
    }
}
