package by.mchs.e112.telephony.service;

import by.mchs.e112.telephony.domain.Call;
import by.mchs.e112.telephony.domain.CallStatus;
import by.mchs.e112.telephony.dto.CallCompleteRequest;
import by.mchs.e112.telephony.dto.CallResponse;
import by.mchs.e112.telephony.dto.CallStartRequest;
import by.mchs.e112.telephony.exception.CallNotFoundException;
import by.mchs.e112.telephony.kafka.CallEventProducer;
import by.mchs.e112.telephony.mapper.CallMapper;
import by.mchs.e112.telephony.repository.CallRepository;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CallService {

    private final CallRepository repository;
    private final CallMapper mapper;
    private final CallEventProducer eventProducer;

    public CallService(CallRepository repository, CallMapper mapper, CallEventProducer eventProducer) {
        this.repository = repository;
        this.mapper = mapper;
        this.eventProducer = eventProducer;
    }

    @Transactional
    public CallResponse start(CallStartRequest request) {
        Call call = new Call(UUID.randomUUID(), request.callerPhone(), request.direction());
        repository.save(call);
        eventProducer.audit("CALL_STARTED", call.getId().toString(), null,
            Map.of("callerPhone", request.callerPhone(), "direction", request.direction().name()));
        return mapper.toResponse(call);
    }

    @Transactional
    public CallResponse answer(UUID id, String operator) {
        Call call = find(id);
        call.answer(operator);
        return mapper.toResponse(call);
    }

    @Transactional
    public CallResponse hold(UUID id) {
        Call call = find(id);
        call.hold();
        return mapper.toResponse(call);
    }

    @Transactional
    public CallResponse resume(UUID id) {
        Call call = find(id);
        call.resume();
        return mapper.toResponse(call);
    }

    @Transactional
    public CallResponse complete(UUID id, CallCompleteRequest request) {
        Call call = find(id);
        call.complete(request.recordingUrl());
        eventProducer.publishReceived(call);
        return mapper.toResponse(call);
    }

    @Transactional
    public CallResponse miss(UUID id) {
        Call call = find(id);
        call.miss();
        eventProducer.audit("CALL_MISSED", id.toString(), null,
            Map.of("callerPhone", call.getCallerPhone()));
        return mapper.toResponse(call);
    }

    @Transactional
    public CallResponse linkIncident(UUID id, UUID incidentId) {
        Call call = find(id);
        call.linkIncident(incidentId);
        return mapper.toResponse(call);
    }

    @Transactional
    public void applyAnalysis(UUID id, String transcript, UUID incidentId) {
        Call call = find(id);
        if (call.getStatus() == CallStatus.COMPLETED) {
            call.attachTranscript(transcript);
        }
        if (call.getStatus() == CallStatus.TRANSCRIBED) {
            call.markAnalyzed(incidentId);
        }
    }

    @Transactional(readOnly = true)
    public CallResponse getById(UUID id) {
        return mapper.toResponse(find(id));
    }

    @Transactional(readOnly = true)
    public List<CallResponse> getActive() {
        return repository.findByStatus(CallStatus.ACTIVE).stream().map(mapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<CallResponse> getByCaller(String phone) {
        return repository.findByCallerPhoneOrderByStartedAtDesc(phone).stream()
            .map(mapper::toResponse).toList();
    }

    private Call find(UUID id) {
        return repository.findById(id).orElseThrow(() -> new CallNotFoundException(id));
    }
}
