package by.mchs.e112.telephony.kafka;

import by.mchs.e112.telephony.domain.Call;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CallEventProducer {

    public static final String TOPIC_RECEIVED = "call.received";
    public static final String TOPIC_AUDIT = "audit.events";

    private static final Logger log = LoggerFactory.getLogger(CallEventProducer.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public CallEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Публикует call.received для ai-service. recordingUrl передаётся для транскрибации Whisper.
     */
    public void publishReceived(Call call) {
        Map<String, Object> event = new HashMap<>();
        event.put("callId", call.getId().toString());
        event.put("callerPhone", call.getCallerPhone());
        event.put("recordingUrl", call.getRecordingUrl() == null ? "" : call.getRecordingUrl());
        event.put("operator", call.getOperator() == null ? "" : call.getOperator());
        event.put("occurredAt", Instant.now().toString());
        send(TOPIC_RECEIVED, call.getId().toString(), event);
        audit("CALL_COMPLETED", call.getId().toString(), call.getOperator(), event);
    }

    public void audit(String action, String entityId, String actor, Map<String, Object> details) {
        Map<String, Object> event = new HashMap<>();
        event.put("service", "telephony-service");
        event.put("action", action);
        event.put("entityType", "CALL");
        event.put("entityId", entityId);
        event.put("actor", actor == null ? "system" : actor);
        event.put("details", details);
        event.put("occurredAt", Instant.now().toString());
        send(TOPIC_AUDIT, entityId, event);
    }

    private void send(String topic, String key, Object payload) {
        kafkaTemplate.send(topic, key, payload).whenComplete((r, ex) -> {
            if (ex != null) {
                log.error("Ошибка публикации в {} ключ {}", topic, key, ex);
            }
        });
    }
}
