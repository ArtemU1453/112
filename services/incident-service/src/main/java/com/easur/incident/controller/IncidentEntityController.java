package com.easur.incident.controller;

import com.easur.incident.dto.CreateRequest;
import com.easur.incident.dto.ItemResponse;
import com.easur.incident.service.IncidentEntityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/incidents")
public class IncidentEntityController {
    private final IncidentEntityService service;

    public IncidentEntityController(IncidentEntityService service) {
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
