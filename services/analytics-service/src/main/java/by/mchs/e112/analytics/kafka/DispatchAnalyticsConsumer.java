package by.mchs.e112.analytics.kafka;

import by.mchs.e112.analytics.service.AnalyticsIngestionService;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Потребитель событий назначения наряда (dispatch.assigned) — наполняет аналитическую витрину
 * назначений. Ошибки обработки логируются и не роняют потребителя.
 */
@Component
public class DispatchAnalyticsConsumer {

    private static final Logger log = LoggerFactory.getLogger(DispatchAnalyticsConsumer.class);

    private final AnalyticsIngestionService ingestionService;

    public DispatchAnalyticsConsumer(AnalyticsIngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }

    @KafkaListener(topics = "dispatch.assigned", groupId = "analytics-service")
    public void onDispatchAssigned(Map<String, Object> event) {
        try {
            ingestionService.ingestDispatchAssigned(event);
        } catch (Exception ex) {
            log.error("Не удалось обработать событие назначения для аналитики: {}", event, ex);
        }
    }
}
