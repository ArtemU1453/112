package com.easur.dispatch.service;

import com.easur.dispatch.dto.CreateRequest;
import com.easur.dispatch.dto.ItemResponse;
import com.easur.dispatch.mapper.DispatchTaskEntityMapper;
import com.easur.dispatch.model.DispatchTaskEntity;
import com.easur.dispatch.repository.DispatchTaskRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class DispatchTaskEntityService {
    private final DispatchTaskRepository repository;
    private final DispatchTaskEntityMapper mapper;

    public DispatchTaskEntityService(DispatchTaskRepository repository, DispatchTaskEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public ItemResponse create(CreateRequest request) {
        DispatchTaskEntity e = new DispatchTaskEntity();
        e.setIncidentReference(request.incidentReference());
        e.setAssignment(request.assignment());
        e.setStatus("PENDING");
        e.setCreatedAt(Instant.now());
        return mapper.toResponse(repository.save(e));
    }

    public List<ItemResponse> list() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }
}
