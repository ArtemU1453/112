package by.mchs.e112.analytics.dto;

/**
 * Гранулярность временного ряда. Значение {@link #pgUnit()} используется как единица date_trunc
 * PostgreSQL; допустимы только перечисленные значения (защита от инъекции).
 */
public enum Granularity {
    HOUR("hour"),
    DAY("day"),
    WEEK("week"),
    MONTH("month");

    private final String pgUnit;

    Granularity(String pgUnit) {
        this.pgUnit = pgUnit;
    }

    public String pgUnit() {
        return pgUnit;
    }
}
