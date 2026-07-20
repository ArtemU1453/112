package com.easur.realtime.controller;

import com.easur.realtime.model.RealtimeEventEntity;
import com.easur.realtime.service.RealtimeEventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/realtime")
public class RealtimeController {

    private final RealtimeEventService service;

    public RealtimeController(RealtimeEventService service) { this.service = service; }

    @PostMapping("/events")
    public ResponseEntity<?> postEvent(@RequestBody RealtimeEventEntity e) {
        RealtimeEventEntity saved = service.handleEvent(e);
        return ResponseEntity.ok(saved);
    }
}
