package by.mchs.e112.incident.kafka;

import by.mchs.e112.incident.domain.IncidentPriority;
import by.mchs.e112.incident.domain.IncidentType;
import by.mchs.e112.incident.dto.IncidentCreateRequest;
import by.mchs.e112.incident.service.IncidentCommandService;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Потребитель call.analyzed от ai-service: автоматически создаёт черновик карточки
 * по результатам ИИ-анализа звонка (тип, адрес, координаты, пострадавшие).
 */
@Component
public class CallAnalyzedConsumer {

    private static final Logger log = LoggerFactory.getLogger(CallAnalyzedConsumer.class);

    private final IncidentCommandService commandService;

    public CallAnalyzedConsumer(IncidentCommandService commandService) {
        this.commandService = commandService;
    }

    @KafkaListener(topics = "call.analyzed", groupId = "incident-service")
    public void onCallAnalyzed(Map<String, Object> event) {
        boolean autoCreate = Boolean.parseBoolean(String.valueOf(event.getOrDefault("autoCreateIncident", "false")));
        if (!autoCreate) {
            return;
        }
        try {
            IncidentType type = parseType(String.valueOf(event.getOrDefault("incidentType", "OTHER")));
            IncidentPriority priority = parsePriority(String.valueOf(event.getOrDefault("priority", "MEDIUM")));
            String transcript = String.valueOf(event.getOrDefault("transcript", ""));
            String address = String.valueOf(event.getOrDefault("address", "Адрес уточняется"));
            Double lat = toDouble(event.get("latitude"));
            Double lon = toDouble(event.get("longitude"));
            String phone = event.get("callerPhone") == null ? null : event.get("callerPhone").toString();
            int casualties = event.get("casualtiesCount") == null
                ? 0 : Integer.parseInt(event.get("casualtiesCount").toString());
            UUID callId = event.get("callId") == null ? null : UUID.fromString(event.get("callId").toString());

            IncidentCreateRequest request = new IncidentCreateRequest(type, priority,
                transcript.isBlank() ? "Автосоздание по звонку 112" : transcript,
                address, lat, lon, phone, null, casualties, callId);
            commandService.create(request, "ai-service");
            log.info("Автоматически создана карточка по звонку {}", callId);
        } catch (Exception ex) {
            log.error("Ошибка автосоздания карточки по call.analyzed: {}", event, ex);
        }
    }

    private IncidentType parseType(String raw) {
        try {
            return IncidentType.valueOf(raw);
        } catch (IllegalArgumentException ex) {
            return IncidentType.OTHER;
        }
    }

    private IncidentPriority parsePriority(String raw) {
        try {
            return IncidentPriority.valueOf(raw);
        } catch (IllegalArgumentException ex) {
            return IncidentPriority.MEDIUM;
        }
    }

    private Double toDouble(Object value) {
        return value == null ? null : Double.valueOf(value.toString());
    }
}
