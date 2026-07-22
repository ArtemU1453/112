package by.mchs.e112.incident.integration;

import static org.assertj.core.api.Assertions.assertThat;

import by.mchs.e112.incident.domain.IncidentPriority;
import by.mchs.e112.incident.domain.IncidentStatus;
import by.mchs.e112.incident.domain.IncidentType;
import by.mchs.e112.incident.dto.IncidentClassifyRequest;
import by.mchs.e112.incident.dto.IncidentCreateRequest;
import by.mchs.e112.incident.dto.IncidentResponse;
import by.mchs.e112.incident.dto.IncidentStatusChangeRequest;
import by.mchs.e112.incident.service.IncidentCommandService;
import by.mchs.e112.incident.service.IncidentQueryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
@Import(IntegrationTestConfig.class)
class IncidentIntegrationIT {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres =
        new PostgreSQLContainer<>(DockerImageName.parse("postgis/postgis:17-3.5")
                .asCompatibleSubstituteFor("postgres"))
            .withDatabaseName("incident_db")
            .withUsername("e112")
            .withPassword("e112secret");

    @MockBean
    private JwtDecoder jwtDecoder;

    @Autowired
    private IncidentCommandService commandService;
    @Autowired
    private IncidentQueryService queryService;

    @Test
    void fullLifecyclePersistsAndTransitions() {
        IncidentCreateRequest create = new IncidentCreateRequest(IncidentType.FIRE,
            IncidentPriority.HIGH, "Задымление на 3 этаже", "Минск, ул. Сурганова 5",
            53.93, 27.58, "+375291234567", "Сергей", 1, null);

        IncidentResponse created = commandService.create(create, "dispatcher1");
        assertThat(created.number()).matches("112-\\d{4}-\\d{6}");
        assertThat(created.status()).isEqualTo(IncidentStatus.RECEIVED.name());

        commandService.classify(created.id(),
            new IncidentClassifyRequest(IncidentType.FIRE, IncidentPriority.CRITICAL), "dispatcher1");

        commandService.changeStatus(created.id(),
            new IncidentStatusChangeRequest(IncidentStatus.DISPATCHED, "Наряд ПАСЧ-1"), "senior1");

        IncidentResponse reloaded = queryService.getById(created.id());
        assertThat(reloaded.status()).isEqualTo(IncidentStatus.DISPATCHED.name());
        assertThat(reloaded.priority()).isEqualTo(IncidentPriority.CRITICAL.name());

        assertThat(queryService.getHistory(created.id())).isNotEmpty();
        assertThat(queryService.getActive()).extracting(IncidentResponse::id).contains(created.id());
    }
}
