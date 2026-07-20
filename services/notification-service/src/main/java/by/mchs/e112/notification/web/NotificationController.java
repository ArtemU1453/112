package by.mchs.e112.notification.web;

import by.mchs.e112.notification.dto.NotificationRequest;
import by.mchs.e112.notification.dto.NotificationResponse;
import by.mchs.e112.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notifications")
@Tag(name = "Уведомления", description = "Отправка и журнал уведомлений системы 112")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SENIOR_DISPATCHER','ROLE_DISPATCHER')")
    @Operation(summary = "Отправить уведомление")
    public NotificationResponse send(@Valid @RequestBody NotificationRequest request) {
        return notificationService.send(request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Уведомление по идентификатору")
    public NotificationResponse getById(@PathVariable UUID id) {
        return notificationService.getById(id);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Журнал уведомлений по связанной сущности")
    public Page<NotificationResponse> getByEntity(@RequestParam String relatedEntityId,
                                                  @PageableDefault(size = 30) Pageable pageable) {
        return notificationService.getByEntity(relatedEntityId, pageable);
    }
}
