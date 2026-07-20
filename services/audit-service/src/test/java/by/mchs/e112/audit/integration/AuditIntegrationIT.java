package by.mchs.e112.audit.integration;

import static org.assertj.core.api.Assertions.assertThat;

import by.mchs.e112.audit.dto.AuditEventResponse;
import by.mchs.e112.audit.dto.AuditFilter;
import by.mchs.e112.audit.service.AuditService;
import java.time.Instant;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
class AuditIntegrationIT {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres =
        new PostgreSQLContainer<>("postgres:17-alpine")
            .withDatabaseName("audit_db").withUsername("e112").withPassword("e112secret");

    @Autowired
    private AuditService auditService;

    @Test
    void recordAndSearchWithJsonbDetails() {
        auditService.record("incident-service", "USER_CREATED", "USER", "u-1", "admin",
            Map.of("username", "dispatcher1", "roles", "ROLE_DISPATCHER"), Instant.now());
        auditService.record("dispatch-service", "UNIT_ASSIGNED", "DISPATCH", "a-1", "dispatcher1",
            Map.of("unitCallSign", "АЦ-1"), Instant.now());

        Page<AuditEventResponse> byService = auditService.search(
            new AuditFilter("dispatch-service", null, null, null, null, null, null),
            PageRequest.of(0, 10));
        assertThat(byService.getTotalElements()).isEqualTo(1);
        assertThat(byService.getContent().get(0).details()).containsEntry("unitCallSign", "АЦ-1");

        Page<AuditEventResponse> byActor = auditService.search(
            new AuditFilter(null, null, null, null, "admin", null, null), PageRequest.of(0, 10));
        assertThat(byActor.getTotalElements()).isEqualTo(1);
        assertThat(byActor.getContent().get(0).action()).isEqualTo("USER_CREATED");
    }
}
