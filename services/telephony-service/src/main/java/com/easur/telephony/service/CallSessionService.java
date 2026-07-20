package com.easur.telephony.service;

import com.easur.telephony.dto.CreateRequest;
import com.easur.telephony.dto.ItemResponse;
import com.easur.telephony.mapper.CallSessionEntityMapper;
import com.easur.telephony.model.CallSessionEntity;
import com.easur.telephony.repository.CallSessionRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class CallSessionService {
    private final CallSessionRepository repository;
    private final CallSessionEntityMapper mapper;

    public CallSessionService(CallSessionRepository repository, CallSessionEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public ItemResponse create(CreateRequest r) {
        CallSessionEntity e = new CallSessionEntity();
        e.setCaller(r.caller());
        e.setDestination(r.destination());
        e.setStatus("NEW");
        e.setCreatedAt(Instant.now());
        return mapper.toResponse(repository.save(e));
    }

    public List<ItemResponse> list() { return repository.findAll().stream().map(mapper::toResponse).toList(); }
}
