package by.mchs.e112.audit.web;

import by.mchs.e112.audit.dto.AuditEventResponse;
import by.mchs.e112.audit.dto.AuditFilter;
import by.mchs.e112.audit.service.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/audit")
@Tag(name = "Аудит", description = "Журнал аудита действий системы 112")
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping("/events")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_ANALYST','ROLE_SENIOR_DISPATCHER')")
    @Operation(summary = "Поиск событий аудита с фильтрами и пагинацией")
    public Page<AuditEventResponse> search(@ModelAttribute AuditFilter filter,
                                           @PageableDefault(size = 50, sort = "occurredAt",
                                               direction = Sort.Direction.DESC) Pageable pageable) {
        return auditService.search(filter, pageable);
    }
}
