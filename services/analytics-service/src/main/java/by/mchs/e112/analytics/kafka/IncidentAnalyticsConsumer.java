package by.mchs.e112.analytics.kafka;

import by.mchs.e112.analytics.service.AnalyticsIngestionService;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Потребитель событий карточки происшествия (incident.created / incident.updated) — наполняет
 * аналитическую витрину происшествий. Ошибки обработки логируются и не роняют потребителя.
 */
@Component
public class IncidentAnalyticsConsumer {

    private static final Logger log = LoggerFactory.getLogger(IncidentAnalyticsConsumer.class);

    private final AnalyticsIngestionService ingestionService;

    public IncidentAnalyticsConsumer(AnalyticsIngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }

    @KafkaListener(topics = {"incident.created", "incident.updated"}, groupId = "analytics-service")
    public void onIncidentEvent(Map<String, Object> event) {
        try {
            ingestionService.ingestIncidentEvent(event);
        } catch (Exception ex) {
            log.error("Не удалось обработать событие происшествия для аналитики: {}", event, ex);
        }
    }
}
