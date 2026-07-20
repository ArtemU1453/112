package com.easur.incident.service;

import com.easur.incident.dto.CreateRequest;
import com.easur.incident.dto.ItemResponse;
import com.easur.incident.mapper.IncidentEntityMapper;
import com.easur.incident.model.IncidentEntity;
import com.easur.incident.repository.IncidentRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class IncidentEntityService {
    private final IncidentRepository repository;
    private final IncidentEntityMapper mapper;

    public IncidentEntityService(IncidentRepository repository, IncidentEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public ItemResponse create(CreateRequest request) {
        IncidentEntity entity = new IncidentEntity();
        entity.setReference("INC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        entity.setTitle(request.title());
        entity.setSeverity(request.severity());
        entity.setStatus("OPEN");
        entity.setAddress(request.address());
        entity.setCreatedAt(Instant.now());
        return mapper.toResponse(repository.save(entity));
    }

    public List<ItemResponse> list() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }
}
