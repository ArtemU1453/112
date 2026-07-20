package by.mchs.e112.dispatch.kafka;

import by.mchs.e112.dispatch.domain.UnitType;
import by.mchs.e112.dispatch.dto.AutoDispatchRequest;
import by.mchs.e112.dispatch.service.DispatchService;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Потребитель incident.created: для критичных происшествий с координатами
 * инициирует автоматический подбор ближайшего свободного подразделения.
 */
@Component
public class IncidentCreatedConsumer {

    private static final Logger log = LoggerFactory.getLogger(IncidentCreatedConsumer.class);

    private final DispatchService dispatchService;

    public IncidentCreatedConsumer(DispatchService dispatchService) {
        this.dispatchService = dispatchService;
    }

    @KafkaListener(topics = "incident.created", groupId = "dispatch-service")
    public void onIncidentCreated(Map<String, Object> event) {
        String priority = String.valueOf(event.getOrDefault("priority", "MEDIUM"));
        if (!"CRITICAL".equals(priority) && !"HIGH".equals(priority)) {
            return;
        }
        Object lat = event.get("latitude");
        Object lon = event.get("longitude");
        if (lat == null || lon == null) {
            log.info("incident.created без координат — автодиспетчеризация пропущена: {}", event.get("number"));
            return;
        }
        try {
            UUID incidentId = UUID.fromString(event.get("incidentId").toString());
            UnitType requiredType = mapType(String.valueOf(event.getOrDefault("type", "OTHER")));
            AutoDispatchRequest request = new AutoDispatchRequest(incidentId, requiredType,
                Double.parseDouble(lat.toString()), Double.parseDouble(lon.toString()));
            dispatchService.autoDispatch(request, "dispatch-service");
            log.info("Автоматически назначен наряд на происшествие {}", event.get("number"));
        } catch (Exception ex) {
            log.warn("Автодиспетчеризация не выполнена для {}: {}", event.get("number"), ex.getMessage());
        }
    }

    private UnitType mapType(String incidentType) {
        return switch (incidentType) {
            case "FIRE" -> UnitType.FIRE_TRUCK;
            case "MEDICAL" -> UnitType.AMBULANCE;
            case "POLICE" -> UnitType.POLICE_PATROL;
            case "GAS_LEAK" -> UnitType.GAS_SERVICE;
            case "TRAFFIC_ACCIDENT" -> UnitType.RESCUE_SQUAD;
            case "WATER_RESCUE" -> UnitType.WATER_RESCUE;
            case "HAZMAT" -> UnitType.HAZMAT_UNIT;
            default -> UnitType.RESCUE_SQUAD;
        };
    }
}
