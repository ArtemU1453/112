package by.mchs.e112.realtime.kafka;

import by.mchs.e112.realtime.web.RealtimeBroadcaster;
import java.util.Map;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Мост Kafka → WebSocket: слушает доменные топики и транслирует события диспетчерам.
 */
@Component
public class EventBridgeConsumer {

    private final RealtimeBroadcaster broadcaster;

    public EventBridgeConsumer(RealtimeBroadcaster broadcaster) {
        this.broadcaster = broadcaster;
    }

    @KafkaListener(topics = "incident.created", groupId = "realtime-service")
    public void onIncidentCreated(Map<String, Object> event) {
        broadcaster.broadcast("/topic/incidents", "INCIDENT_CREATED", event);
    }

    @KafkaListener(topics = "incident.updated", groupId = "realtime-service")
    public void onIncidentUpdated(Map<String, Object> event) {
        broadcaster.broadcast("/topic/incidents", "INCIDENT_UPDATED", event);
    }

    @KafkaListener(topics = "dispatch.assigned", groupId = "realtime-service")
    public void onDispatchAssigned(Map<String, Object> event) {
        broadcaster.broadcast("/topic/dispatch", "DISPATCH_ASSIGNED", event);
    }

    @KafkaListener(topics = "unit.status-changed", groupId = "realtime-service")
    public void onUnitStatusChanged(Map<String, Object> event) {
        broadcaster.broadcast("/topic/units", "UNIT_STATUS_CHANGED", event);
    }

    @KafkaListener(topics = "call.analyzed", groupId = "realtime-service")
    public void onCallAnalyzed(Map<String, Object> event) {
        broadcaster.broadcast("/topic/calls", "CALL_ANALYZED", event);
    }
}
