package by.mchs.e112.analytics.kafka;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class EventValuesTest {

    @Test
    void parsesUuidOrReturnsNull() {
        UUID id = UUID.randomUUID();
        assertThat(EventValues.optUuid(id.toString())).isEqualTo(id);
        assertThat(EventValues.optUuid("не-uuid")).isNull();
        assertThat(EventValues.optUuid(null)).isNull();
    }

    @Test
    void parsesInstantFromSupportedForms() {
        Instant iso = Instant.parse("2026-07-20T08:00:00Z");
        Instant fallback = Instant.parse("2000-01-01T00:00:00Z");
        assertThat(EventValues.toInstant(iso.toString(), fallback)).isEqualTo(iso);
        assertThat(EventValues.toInstant(iso, fallback)).isEqualTo(iso);
        assertThat(EventValues.toInstant(iso.toEpochMilli(), fallback)).isEqualTo(iso);
        assertThat(EventValues.toInstant(iso.getEpochSecond(), fallback)).isEqualTo(iso);
        assertThat(EventValues.toInstant(java.sql.Timestamp.from(iso), fallback)).isEqualTo(iso);
        assertThat(EventValues.toInstant(List.of(iso.getEpochSecond(), 0), fallback)).isEqualTo(iso);
        assertThat(EventValues.toInstant("мусор", fallback)).isEqualTo(fallback);
        assertThat(EventValues.toInstant(null, fallback)).isEqualTo(fallback);
    }

    @Test
    void parsesNumbersDefensively() {
        assertThat(EventValues.toInt(5, 0)).isEqualTo(5);
        assertThat(EventValues.toInt("7", 0)).isEqualTo(7);
        assertThat(EventValues.toInt("x", 3)).isEqualTo(3);
        assertThat(EventValues.toInt(null, 9)).isEqualTo(9);
        assertThat(EventValues.toDoubleOrNull(2.5)).isEqualTo(2.5);
        assertThat(EventValues.toDoubleOrNull("3.5")).isEqualTo(3.5);
        assertThat(EventValues.toDoubleOrNull("x")).isNull();
        assertThat(EventValues.toDoubleOrNull(null)).isNull();
    }

    @Test
    void readsStringWithDefault() {
        Map<String, Object> event = Map.of("type", "FIRE");
        assertThat(EventValues.str(event, "type", "UNKNOWN")).isEqualTo("FIRE");
        assertThat(EventValues.str(event, "missing", "UNKNOWN")).isEqualTo("UNKNOWN");
    }
}
