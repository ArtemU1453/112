package com.easur.telephony.mapper;

import com.easur.telephony.dto.ItemResponse;
import com.easur.telephony.model.CallSessionEntity;
import org.springframework.stereotype.Component;

@Component
public class CallSessionEntityMapper {
    public ItemResponse toResponse(CallSessionEntity e) {
        return new ItemResponse(e.getId(), e.getCaller(), e.getDestination(), e.getStatus());
    }
}
