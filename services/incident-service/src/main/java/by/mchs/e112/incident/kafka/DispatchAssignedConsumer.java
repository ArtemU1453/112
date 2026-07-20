package by.mchs.e112.incident.kafka;

import by.mchs.e112.incident.service.IncidentCommandService;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Потребитель dispatch.assigned: при назначении наряда переводит карточку в DISPATCHED.
 */
@Component
public class DispatchAssignedConsumer {

    private static final Logger log = LoggerFactory.getLogger(DispatchAssignedConsumer.class);

    private final IncidentCommandService commandService;

    public DispatchAssignedConsumer(IncidentCommandService commandService) {
        this.commandService = commandService;
    }

    @KafkaListener(topics = "dispatch.assigned", groupId = "incident-service")
    public void onDispatchAssigned(Map<String, Object> event) {
        Object incidentIdRaw = event.get("incidentId");
        if (incidentIdRaw == null) {
            log.warn("Событие dispatch.assigned без incidentId: {}", event);
            return;
        }
        UUID incidentId = UUID.fromString(incidentIdRaw.toString());
        String unit = String.valueOf(event.getOrDefault("unitCallSign", "неизвестный наряд"));
        String actor = String.valueOf(event.getOrDefault("actor", "dispatch-service"));
        try {
            commandService.markDispatched(incidentId, actor, unit);
        } catch (Exception ex) {
            log.error("Не удалось обработать dispatch.assigned для {}", incidentId, ex);
        }
    }
}
