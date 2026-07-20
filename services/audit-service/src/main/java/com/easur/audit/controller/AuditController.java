package com.easur.audit.controller;

import com.easur.audit.model.AuditLogEntity;
import com.easur.audit.service.AuditService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/audit")
public class AuditController {

    private final AuditService service;

    public AuditController(AuditService service) { this.service = service; }

    @PostMapping("/logs")
    public ResponseEntity<?> createLog(@RequestBody AuditLogEntity e) {
        AuditLogEntity saved = service.record(e);
        return ResponseEntity.ok(saved);
    }
}
