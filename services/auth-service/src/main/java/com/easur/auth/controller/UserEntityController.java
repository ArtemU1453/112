package com.easur.auth.controller;

import com.easur.auth.dto.CreateRequest;
import com.easur.auth.dto.ItemResponse;
import com.easur.auth.service.UserEntityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class UserEntityController {
    private final UserEntityService service;

    public UserEntityController(UserEntityService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemResponse create(@Valid @RequestBody CreateRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<ItemResponse> list() {
        return service.list();
    }
}
