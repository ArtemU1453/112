package by.mchs.e112.incident.kafka;

import by.mchs.e112.incident.domain.Incident;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class IncidentEventProducer {

    public static final String TOPIC_CREATED = "incident.created";
    public static final String TOPIC_UPDATED = "incident.updated";

    private static final Logger log = LoggerFactory.getLogger(IncidentEventProducer.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public IncidentEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishCreated(Incident incident, String actor) {
        send(TOPIC_CREATED, toEvent(incident, actor));
    }

    public void publishUpdated(Incident incident, String actor) {
        send(TOPIC_UPDATED, toEvent(incident, actor));
    }

    private void send(String topic, IncidentEvent event) {
        kafkaTemplate.send(topic, event.incidentId().toString(), event)
            .whenComplete((result, ex) -> {
                if (ex != null) {
                    log.error("Ошибка публикации события в {} для {}", topic, event.incidentId(), ex);
                } else {
                    log.debug("Событие {} опубликовано, offset={}", topic,
                        result.getRecordMetadata().offset());
                }
            });
    }

    private IncidentEvent toEvent(Incident incident, String actor) {
        return new IncidentEvent(
            incident.getId(),
            incident.getNumber(),
            incident.getType().name(),
            incident.getPriority().name(),
            incident.getStatus().name(),
            incident.getAddress(),
            incident.getLatitude(),
            incident.getLongitude(),
            incident.getDescription(),
            incident.getCasualtiesCount(),
            actor,
            Instant.now()
        );
    }
}
