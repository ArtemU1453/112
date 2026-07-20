package by.mchs.e112.realtime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import by.mchs.e112.realtime.dto.RealtimeEvent;
import by.mchs.e112.realtime.kafka.EventBridgeConsumer;
import by.mchs.e112.realtime.web.RealtimeBroadcaster;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.messaging.simp.SimpMessagingTemplate;

class EventBridgeConsumerTest {

    @Test
    void realtimeEventFactorySetsTypeAndTimestamp() {
        RealtimeEvent event = RealtimeEvent.of("INCIDENT_CREATED", Map.of("number", "112-2026-000001"));
        assertThat(event.type()).isEqualTo("INCIDENT_CREATED");
        assertThat(event.payload()).containsEntry("number", "112-2026-000001");
        assertThat(event.timestamp()).isNotNull();
    }

    @Test
    void incidentCreatedBroadcastToIncidentsTopic() {
        SimpMessagingTemplate template = Mockito.mock(SimpMessagingTemplate.class);
        RealtimeBroadcaster broadcaster = new RealtimeBroadcaster(template);
        EventBridgeConsumer consumer = new EventBridgeConsumer(broadcaster);

        consumer.onIncidentCreated(Map.of("incidentId", "1", "number", "112-2026-000001"));

        ArgumentCaptor<RealtimeEvent> captor = ArgumentCaptor.forClass(RealtimeEvent.class);
        verify(template).convertAndSend(eq("/topic/incidents"), captor.capture());
        assertThat(captor.getValue().type()).isEqualTo("INCIDENT_CREATED");
    }

    @Test
    void unitStatusBroadcastToUnitsTopic() {
        SimpMessagingTemplate template = Mockito.mock(SimpMessagingTemplate.class);
        EventBridgeConsumer consumer = new EventBridgeConsumer(new RealtimeBroadcaster(template));
        consumer.onUnitStatusChanged(Map.of("unitId", "u1", "status", "DISPATCHED"));
        verify(template).convertAndSend(eq("/topic/units"), any(RealtimeEvent.class));
    }
}
