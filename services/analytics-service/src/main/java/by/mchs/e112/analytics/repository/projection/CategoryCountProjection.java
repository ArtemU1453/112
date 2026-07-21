package by.mchs.e112.analytics.repository.projection;

/**
 * Проекция группировки «категория → количество» (JPQL).
 */
public interface CategoryCountProjection {

    String getCategory();

    long getCount();
}
