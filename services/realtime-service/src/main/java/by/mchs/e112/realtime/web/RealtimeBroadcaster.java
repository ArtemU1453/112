package by.mchs.e112.realtime.web;

import by.mchs.e112.realtime.dto.RealtimeEvent;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * Отправляет события в STOMP-топики для подписанных диспетчеров.
 */
@Component
public class RealtimeBroadcaster {

    private static final Logger log = LoggerFactory.getLogger(RealtimeBroadcaster.class);

    private final SimpMessagingTemplate messagingTemplate;

    public RealtimeBroadcaster(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void broadcast(String topic, String type, Map<String, Object> payload) {
        RealtimeEvent event = RealtimeEvent.of(type, payload);
        messagingTemplate.convertAndSend(topic, event);
        log.debug("Транслировано событие {} в {}", type, topic);
    }
}
