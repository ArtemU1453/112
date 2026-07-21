package by.mchs.e112.analytics.dto;

/**
 * Слайс группировки «категория → количество» для операционной аналитики.
 */
public record CategoryCount(String category, long count) {
}
