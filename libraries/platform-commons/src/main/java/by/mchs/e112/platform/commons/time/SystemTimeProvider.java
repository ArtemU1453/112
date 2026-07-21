package by.mchs.e112.platform.commons.time;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

/**
 * Реализация {@link TimeProvider} поверх системных часов. Внутреннее время — UTC;
 * зона отображения по умолчанию — Europe/Minsk (Time Service).
 */
public final class SystemTimeProvider implements TimeProvider {

    private final Clock clock;
    private final ZoneId defaultZone;

    public SystemTimeProvider() {
        this(Clock.systemUTC(), TimeZones.DEFAULT);
    }

    public SystemTimeProvider(Clock clock, ZoneId defaultZone) {
        this.clock = clock;
        this.defaultZone = defaultZone;
    }

    @Override
    public Instant now() {
        return clock.instant();
    }

    @Override
    public ZoneId defaultZone() {
        return defaultZone;
    }
}
