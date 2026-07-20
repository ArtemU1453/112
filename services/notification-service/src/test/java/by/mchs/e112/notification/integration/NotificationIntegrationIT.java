package by.mchs.e112.notification.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import by.mchs.e112.notification.domain.NotificationChannel;
import by.mchs.e112.notification.dto.NotificationRequest;
import by.mchs.e112.notification.dto.NotificationResponse;
import by.mchs.e112.notification.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
@Import(NotificationIntegrationIT.MockMail.class)
class NotificationIntegrationIT {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres =
        new PostgreSQLContainer<>("postgres:17-alpine")
            .withDatabaseName("notification_db").withUsername("e112").withPassword("e112secret");

    @Autowired
    private NotificationService notificationService;

    @Test
    void pushNotificationIsSentAndJournaled() {
        NotificationResponse response = notificationService.send(new NotificationRequest(
            NotificationChannel.PUSH, "АЦ-1", "Выезд", "Назначено на происшествие", "inc-1"));
        assertThat(response.status()).isEqualTo("SENT");
        assertThat(response.channel()).isEqualTo("PUSH");
    }

    @Test
    void smsDryRunMarkedSent() {
        NotificationResponse response = notificationService.send(new NotificationRequest(
            NotificationChannel.SMS, "+375291234567", null, "Тестовое SMS", "inc-2"));
        assertThat(response.status()).isEqualTo("SENT");
    }

    @TestConfiguration
    static class MockMail {
        @Bean
        @Primary
        JavaMailSender javaMailSender() {
            return mock(JavaMailSender.class);
        }
    }
}
