package com.easur.gis.controller;

import com.easur.gis.dto.CreateRequest;
import com.easur.gis.dto.ItemResponse;
import com.easur.gis.service.GeoMapService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/geo")
public class GeoMapController {
    private final GeoMapService service;
    public GeoMapController(GeoMapService service) { this.service = service; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemResponse create(@Valid @RequestBody CreateRequest r) { return service.create(r); }

    @GetMapping
    public List<ItemResponse> list() { return service.list(); }
}
