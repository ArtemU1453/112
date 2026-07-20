package by.mchs.e112.dispatch.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class GeoDistanceTest {

    @Test
    void haversineMatchesKnownDistanceMinskToGomel() {
        // Минск (53.9006, 27.5590) — Гомель (52.4345, 30.9754): ~270 км
        double km = GeoDistance.haversineKm(53.9006, 27.5590, 52.4345, 30.9754);
        assertThat(km).isBetween(260.0, 285.0);
    }

    @Test
    void zeroDistanceForSamePoint() {
        assertThat(GeoDistance.haversineKm(53.9, 27.56, 53.9, 27.56)).isZero();
    }
}
