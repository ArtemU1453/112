package com.easur.gis.service;

import com.easur.gis.dto.CreateRequest;
import com.easur.gis.dto.ItemResponse;
import com.easur.gis.mapper.GeoMapEntityMapper;
import com.easur.gis.model.GeoMapEntity;
import com.easur.gis.repository.GeoMapRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class GeoMapService {
    private final GeoMapRepository repository;
    private final GeoMapEntityMapper mapper;

    public GeoMapService(GeoMapRepository repository, GeoMapEntityMapper mapper) { this.repository = repository; this.mapper = mapper; }

    public ItemResponse create(CreateRequest r) {
        GeoMapEntity e = new GeoMapEntity();
        e.setName(r.name()); e.setLatitude(r.latitude()); e.setLongitude(r.longitude()); e.setCreatedAt(Instant.now());
        return mapper.toResponse(repository.save(e));
    }

    public List<ItemResponse> list() { return repository.findAll().stream().map(mapper::toResponse).toList(); }
}
