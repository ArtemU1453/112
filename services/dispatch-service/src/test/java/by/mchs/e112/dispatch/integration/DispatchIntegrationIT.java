package by.mchs.e112.dispatch.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import by.mchs.e112.dispatch.domain.UnitType;
import by.mchs.e112.dispatch.dto.AssignmentResponse;
import by.mchs.e112.dispatch.dto.AutoDispatchRequest;
import by.mchs.e112.dispatch.dto.UnitCreateRequest;
import by.mchs.e112.dispatch.dto.UnitResponse;
import by.mchs.e112.dispatch.service.DispatchService;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
@Import(DispatchIntegrationIT.MockKafka.class)
class DispatchIntegrationIT {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres =
        new PostgreSQLContainer<>(DockerImageName.parse("postgis/postgis:17-3.5")
                .asCompatibleSubstituteFor("postgres"))
            .withDatabaseName("dispatch_db").withUsername("e112").withPassword("e112secret");

    @MockBean
    private JwtDecoder jwtDecoder;

    @Autowired
    private DispatchService dispatchService;

    @Test
    void registerUnitThenAutoDispatch() {
        UnitResponse unit = dispatchService.createUnit(new UnitCreateRequest(
            "АЦ-101", UnitType.FIRE_TRUCK, UUID.randomUUID(), "ПАСЧ-1 Минск", 6,
            53.90, 27.56), "admin");
        assertThat(unit.status()).isEqualTo("AVAILABLE");

        UUID incidentId = UUID.randomUUID();
        AssignmentResponse assignment = dispatchService.autoDispatch(
            new AutoDispatchRequest(incidentId, UnitType.FIRE_TRUCK, 53.91, 27.57), "dispatcher1");

        assertThat(assignment.unitCallSign()).isEqualTo("АЦ-101");
        assertThat(assignment.status()).isEqualTo("ACTIVE");

        assertThat(dispatchService.getUnit(unit.id()).status()).isEqualTo("DISPATCHED");
        assertThat(dispatchService.getAvailableUnits()).isEmpty();
        assertThat(dispatchService.getAssignmentsByIncident(incidentId)).hasSize(1);
    }

    @TestConfiguration
    static class MockKafka {
        @Bean
        @Primary
        @SuppressWarnings("unchecked")
        KafkaTemplate<String, Object> kafkaTemplate() {
            return mock(KafkaTemplate.class);
        }
    }
}
