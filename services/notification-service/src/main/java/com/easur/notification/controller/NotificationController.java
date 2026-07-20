package com.easur.notification.controller;

import com.easur.notification.dto.NotificationRequest;
import com.easur.notification.model.NotificationEntity;
import com.easur.notification.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody NotificationRequest req) {
        NotificationEntity e = new NotificationEntity();
        e.setType(req.type);
        e.setMessage(req.message);
        e.setRecipient(req.recipient);
        NotificationEntity saved = service.sendNotification(e);
        return ResponseEntity.ok(saved);
    }
}
