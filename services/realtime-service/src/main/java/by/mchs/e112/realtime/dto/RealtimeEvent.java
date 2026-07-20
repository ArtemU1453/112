package by.mchs.e112.realtime.dto;

import java.time.Instant;
import java.util.Map;

/**
 * Обобщённое событие для трансляции клиентам через WebSocket.
 */
public record RealtimeEvent(
    String type,
    Map<String, Object> payload,
    Instant timestamp
) {
    public static RealtimeEvent of(String type, Map<String, Object> payload) {
        return new RealtimeEvent(type, payload, Instant.now());
    }
}
