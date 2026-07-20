package com.easur.telephony.controller;

import com.easur.telephony.dto.CreateRequest;
import com.easur.telephony.dto.ItemResponse;
import com.easur.telephony.service.CallSessionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/calls")
public class CallSessionController {
    private final CallSessionService service;
    public CallSessionController(CallSessionService service) { this.service = service; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemResponse create(@Valid @RequestBody CreateRequest r) { return service.create(r); }

    @GetMapping
    public List<ItemResponse> list() { return service.list(); }
}
