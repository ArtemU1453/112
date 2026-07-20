package com.easur.auth.service;

import com.easur.auth.dto.CreateRequest;
import com.easur.auth.dto.ItemResponse;
import com.easur.auth.mapper.UserEntityMapper;
import com.easur.auth.model.UserEntity;
import com.easur.auth.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class UserEntityService {
    private final UserRepository repository;
    private final UserEntityMapper mapper;

    public UserEntityService(UserRepository repository, UserEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public ItemResponse create(CreateRequest request) {
        UserEntity entity = new UserEntity();
        entity.setUsername(request.username());
        entity.setEmail(request.email());
        entity.setPasswordHash(request.passwordHash());
        entity.setRole(request.role());
        entity.setEnabled(true);
        entity.setCreatedAt(Instant.now());
        entity.setUpdatedAt(Instant.now());
        return mapper.toResponse(repository.save(entity));
    }

    public List<ItemResponse> list() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }
}
