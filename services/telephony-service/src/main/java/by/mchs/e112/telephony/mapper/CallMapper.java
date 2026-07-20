package by.mchs.e112.telephony.mapper;

import by.mchs.e112.telephony.domain.Call;
import by.mchs.e112.telephony.dto.CallResponse;
import org.springframework.stereotype.Component;

@Component
public class CallMapper {

    public CallResponse toResponse(Call call) {
        return new CallResponse(
            call.getId(),
            call.getCallerPhone(),
            call.getDirection().name(),
            call.getStatus().name(),
            call.getOperator(),
            call.getRecordingUrl(),
            call.getTranscript(),
            call.getIncidentId(),
            call.getStartedAt(),
            call.getAnsweredAt(),
            call.getEndedAt(),
            call.getDurationSeconds());
    }
}
