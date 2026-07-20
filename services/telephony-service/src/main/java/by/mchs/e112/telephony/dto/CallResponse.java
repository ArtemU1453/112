package by.mchs.e112.telephony.dto;

import java.time.Instant;
import java.util.UUID;

public record CallResponse(
    UUID id,
    String callerPhone,
    String direction,
    String status,
    String operator,
    String recordingUrl,
    String transcript,
    UUID incidentId,
    Instant startedAt,
    Instant answeredAt,
    Instant endedAt,
    Long durationSeconds
) {
}
