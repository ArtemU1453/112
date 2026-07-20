package com.easur.dispatch.mapper;

import com.easur.dispatch.dto.ItemResponse;
import com.easur.dispatch.model.DispatchTaskEntity;
import org.springframework.stereotype.Component;

@Component
public class DispatchTaskEntityMapper {
    public ItemResponse toResponse(DispatchTaskEntity e) {
        return new ItemResponse(e.getId(), e.getIncidentReference(), e.getAssignment(), e.getStatus());
    }
}
