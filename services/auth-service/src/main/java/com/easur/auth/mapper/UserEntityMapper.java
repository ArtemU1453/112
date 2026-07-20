package com.easur.auth.mapper;

import com.easur.auth.dto.ItemResponse;
import com.easur.auth.model.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserEntityMapper {
    public ItemResponse toResponse(UserEntity entity) {
        return new ItemResponse(entity.getId(), entity.getUsername(), entity.getEmail(), entity.getRole());
    }
}
