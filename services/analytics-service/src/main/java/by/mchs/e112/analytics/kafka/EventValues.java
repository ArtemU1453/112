package by.mchs.e112.analytics.kafka;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Толерантный разбор значений интеграционных событий и результатов запросов. Не бросает исключений
 * на некорректных данных — возвращает значения по умолчанию/{@code null}, что защищает потребитель
 * Kafka от «отравленных» сообщений (обработка ошибок обязательна, заглушки запрещены).
 */
public final class EventValues {

    private EventValues() {
    }

    public static String str(Map<String, Object> event, String key, String defaultValue) {
        Object value = event.get(key);
        if (value == null) {
            return defaultValue;
        }
        String s = value.toString().trim();
        return s.isEmpty() ? defaultValue : s;
    }

    public static UUID optUuid(Object value) {
        if (value == null) {
            return null;
        }
        try {
            return UUID.fromString(value.toString().trim());
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    public static int toInt(Object value, int defaultValue) {
        if (value instanceof Number number) {
            return number.intValue();
        }
        if (value != null) {
            try {
                return Integer.parseInt(value.toString().trim());
            } catch (NumberFormatException ignored) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    public static Double toDoubleOrNull(Object value) {
        if (value instanceof Number number) {
            return number.doubleValue();
        }
        if (value != null) {
            try {
                return Double.parseDouble(value.toString().trim());
            } catch (NumberFormatException ignored) {
                return null;
            }
        }
        return null;
    }

    /**
     * Преобразует значение времени из события или результата SQL в {@link Instant}. Поддерживает
     * java.time.*, java.sql.Timestamp, ISO-8601 строку, epoch (сек/мс) и массив [seconds, nanos].
     */
    public static Instant toInstant(Object value, Instant fallback) {
        switch (value) {
            case null -> {
                return fallback;
            }
            case Instant instant -> {
                return instant;
            }
            case OffsetDateTime offsetDateTime -> {
                return offsetDateTime.toInstant();
            }
            case ZonedDateTime zonedDateTime -> {
                return zonedDateTime.toInstant();
            }
            case LocalDateTime localDateTime -> {
                return localDateTime.toInstant(ZoneOffset.UTC);
            }
            case java.sql.Timestamp timestamp -> {
                return timestamp.toInstant();
            }
            case java.util.Date date -> {
                return date.toInstant();
            }
            case Number number -> {
                long epoch = number.longValue();
                return epoch >= 100_000_000_000L
                    ? Instant.ofEpochMilli(epoch)
                    : Instant.ofEpochSecond(epoch);
            }
            case List<?> parts -> {
                if (parts.size() >= 2 && parts.get(0) instanceof Number sec
                    && parts.get(1) instanceof Number nanos) {
                    return Instant.ofEpochSecond(sec.longValue(), nanos.longValue());
                }
                return fallback;
            }
            case String text -> {
                try {
                    return Instant.parse(text.trim());
                } catch (Exception ignored) {
                    return fallback;
                }
            }
            default -> {
                return fallback;
            }
        }
    }
}
