package by.mchs.e112.platform.commons.time;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/** Константы часовых поясов и форматов времени (Time Service / Localization). */
public final class TimeZones {

    /** Часовой пояс по умолчанию для отображения. */
    public static final ZoneId DEFAULT = ZoneId.of("Europe/Minsk");

    /** UTC — внутреннее хранение и обмен. */
    public static final ZoneId UTC = ZoneId.of("UTC");

    /** ISO-8601 с миллисекундами и зоной (единый формат журналов/событий). */
    public static final DateTimeFormatter ISO = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    private TimeZones() {
    }
}
