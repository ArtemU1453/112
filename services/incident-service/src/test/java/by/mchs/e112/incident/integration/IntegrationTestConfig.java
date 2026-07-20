package by.mchs.e112.incident.integration;

import static org.mockito.Mockito.mock;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * Тестовая конфигурация: подменяет KafkaTemplate моком и заменяет Redis-кэш
 * на in-memory, чтобы интеграционные тесты требовали только PostgreSQL (Testcontainers).
 * KafkaAutoConfiguration исключён в application-test.yml, поэтому @KafkaListener не активируется.
 */
@TestConfiguration
public class IntegrationTestConfig {

    @Bean
    @Primary
    @SuppressWarnings("unchecked")
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return mock(KafkaTemplate.class);
    }

    @Bean
    @Primary
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("incident-stats");
    }
}
