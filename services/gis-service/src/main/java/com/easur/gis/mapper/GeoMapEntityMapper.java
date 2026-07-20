package com.easur.gis.mapper;

import com.easur.gis.dto.ItemResponse;
import com.easur.gis.model.GeoMapEntity;
import org.springframework.stereotype.Component;

@Component
public class GeoMapEntityMapper {
    public ItemResponse toResponse(GeoMapEntity e) { return new ItemResponse(e.getId(), e.getName(), e.getLatitude(), e.getLongitude()); }
}
