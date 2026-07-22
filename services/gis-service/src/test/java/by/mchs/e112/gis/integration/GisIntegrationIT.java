package by.mchs.e112.gis.integration;

import static org.assertj.core.api.Assertions.assertThat;

import by.mchs.e112.gis.domain.ServiceType;
import by.mchs.e112.gis.dto.GeocodeResponse;
import by.mchs.e112.gis.dto.NearestStationResponse;
import by.mchs.e112.gis.dto.ResponseZoneResponse;
import by.mchs.e112.gis.dto.ReverseGeocodeResponse;
import by.mchs.e112.gis.service.GeoService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
class GisIntegrationIT {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres =
        new PostgreSQLContainer<>(DockerImageName.parse("postgis/postgis:17-3.5")
                .asCompatibleSubstituteFor("postgres"))
            .withDatabaseName("gis_db").withUsername("e112").withPassword("e112secret");

    @MockBean
    private JwtDecoder jwtDecoder;

    @Autowired
    private GeoService geoService;

    @Test
    void geocodeFindsSeededAddress() {
        GeocodeResponse response = geoService.geocode("проспект Независимости 10");
        assertThat(response.matchedAddress()).contains("Независимости");
        assertThat(response.latitude()).isBetween(53.8, 54.0);
        assertThat(response.longitude()).isBetween(27.5, 27.7);
    }

    @Test
    void reverseGeocodeReturnsNearestAddress() {
        ReverseGeocodeResponse response = geoService.reverseGeocode(53.9045, 27.5665);
        assertThat(response.address()).isNotBlank();
        assertThat(response.distanceMeters()).isLessThan(500.0);
    }

    @Test
    void nearestFireStationsSortedByDistance() {
        List<NearestStationResponse> stations =
            geoService.nearestStations(53.9010, 27.5595, ServiceType.FIRE, 2);
        assertThat(stations).isNotEmpty();
        assertThat(stations.get(0).name()).contains("ПАСЧ-1");
        assertThat(stations.get(0).distanceKm()).isLessThan(2.0);
    }

    @Test
    void zoneContainsCentralPoint() {
        List<ResponseZoneResponse> zones = geoService.zonesContaining(53.9010, 27.5595);
        assertThat(zones).extracting(ResponseZoneResponse::name).contains("Зона ПАСЧ-1 центр");
    }
}
