package com.easur.dispatch.controller;

import com.easur.dispatch.dto.CreateRequest;
import com.easur.dispatch.dto.ItemResponse;
import com.easur.dispatch.service.DispatchTaskEntityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dispatch")
public class DispatchTaskController {
    private final DispatchTaskEntityService service;

    public DispatchTaskController(DispatchTaskEntityService service) { this.service = service; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemResponse create(@Valid @RequestBody CreateRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<ItemResponse> list() { return service.list(); }
}
