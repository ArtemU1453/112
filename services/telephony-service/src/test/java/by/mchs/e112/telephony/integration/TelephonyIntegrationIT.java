package by.mchs.e112.telephony.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import by.mchs.e112.telephony.domain.CallDirection;
import by.mchs.e112.telephony.dto.CallCompleteRequest;
import by.mchs.e112.telephony.dto.CallResponse;
import by.mchs.e112.telephony.dto.CallStartRequest;
import by.mchs.e112.telephony.service.CallService;
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

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
@Import(TelephonyIntegrationIT.MockKafka.class)
class TelephonyIntegrationIT {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres =
        new PostgreSQLContainer<>("postgres:17-alpine")
            .withDatabaseName("telephony_db").withUsername("e112").withPassword("e112secret");

    @MockBean
    private JwtDecoder jwtDecoder;

    @Autowired
    private CallService callService;

    @Test
    void callLifecyclePersistsAndAnalysisApplied() {
        CallResponse started = callService.start(
            new CallStartRequest("+375291234567", CallDirection.INBOUND));
        assertThat(started.status()).isEqualTo("RINGING");

        callService.answer(started.id(), "dispatcher1");
        CallResponse completed = callService.complete(started.id(),
            new CallCompleteRequest("http://records/1.wav"));
        assertThat(completed.status()).isEqualTo("COMPLETED");

        UUID incidentId = UUID.randomUUID();
        callService.applyAnalysis(started.id(), "Пожар на Немиге, есть пострадавшие", incidentId);

        CallResponse analyzed = callService.getById(started.id());
        assertThat(analyzed.status()).isEqualTo("ANALYZED");
        assertThat(analyzed.transcript()).contains("Пожар");
        assertThat(analyzed.incidentId()).isEqualTo(incidentId);
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
