package by.mchs.e112.telephony.kafka;

import by.mchs.e112.telephony.service.CallService;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Потребитель call.analyzed от ai-service: сохраняет транскрипт и переводит вызов в ANALYZED.
 */
@Component
public class CallAnalyzedConsumer {

    private static final Logger log = LoggerFactory.getLogger(CallAnalyzedConsumer.class);

    private final CallService callService;

    public CallAnalyzedConsumer(CallService callService) {
        this.callService = callService;
    }

    @KafkaListener(topics = "call.analyzed", groupId = "telephony-service")
    public void onCallAnalyzed(Map<String, Object> event) {
        Object callIdRaw = event.get("callId");
        if (callIdRaw == null) {
            return;
        }
        try {
            UUID callId = UUID.fromString(callIdRaw.toString());
            String transcript = String.valueOf(event.getOrDefault("transcript", ""));
            UUID incidentId = event.get("incidentId") == null
                ? null : UUID.fromString(event.get("incidentId").toString());
            callService.applyAnalysis(callId, transcript, incidentId);
            log.info("Вызов {} обновлён результатами ИИ-анализа", callId);
        } catch (Exception ex) {
            log.error("Ошибка обработки call.analyzed: {}", event, ex);
        }
    }
}
