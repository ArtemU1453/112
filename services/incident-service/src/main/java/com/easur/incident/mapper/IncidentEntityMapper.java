package com.easur.incident.mapper;

import com.easur.incident.dto.ItemResponse;
import com.easur.incident.model.IncidentEntity;
import org.springframework.stereotype.Component;

@Component
public class IncidentEntityMapper {
    public ItemResponse toResponse(IncidentEntity entity) {
        return new ItemResponse(entity.getId(), entity.getReference(), entity.getTitle(), entity.getSeverity(), entity.getStatus());
    }
}
