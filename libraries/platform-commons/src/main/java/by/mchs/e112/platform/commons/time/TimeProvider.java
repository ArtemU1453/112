package by.mchs.e112.platform.commons.time;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Единый источник времени Core Platform (Time Service). Инъекция вместо прямого
 * {@code Instant.now()} обеспечивает тестируемость и единообразие часовых поясов.
 */
public interface TimeProvider {

    /** Текущий момент времени в UTC. */
    Instant now();

    /** Текущее время в заданном часовом поясе. */
    default ZonedDateTime now(ZoneId zone) {
        return now().atZone(zone);
    }

    /** Часовой пояс по умолчанию для отображения (Localization/Time Service). */
    ZoneId defaultZone();
}
