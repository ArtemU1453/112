package by.mchs.e112.incident.web;

import by.mchs.e112.incident.dto.IncidentClassifyRequest;
import by.mchs.e112.incident.dto.IncidentCreateRequest;
import by.mchs.e112.incident.dto.IncidentFilter;
import by.mchs.e112.incident.dto.IncidentHistoryResponse;
import by.mchs.e112.incident.dto.IncidentResponse;
import by.mchs.e112.incident.dto.IncidentStatsResponse;
import by.mchs.e112.incident.dto.IncidentStatusChangeRequest;
import by.mchs.e112.incident.dto.IncidentUpdateRequest;
import by.mchs.e112.incident.service.IncidentCommandService;
import by.mchs.e112.incident.service.IncidentQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/incidents")
@Tag(name = "Происшествия", description = "Карточки происшествий системы 112")
public class IncidentController {

    private final IncidentCommandService commandService;
    private final IncidentQueryService queryService;

    public IncidentController(IncidentCommandService commandService, IncidentQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ROLE_DISPATCHER','ROLE_SENIOR_DISPATCHER')")
    @Operation(summary = "Создать карточку происшествия")
    public IncidentResponse create(@Valid @RequestBody IncidentCreateRequest request, Authentication auth) {
        return commandService.create(request, auth.getName());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_DISPATCHER','ROLE_SENIOR_DISPATCHER')")
    @Operation(summary = "Обновить данные карточки")
    public IncidentResponse update(@PathVariable UUID id,
                                   @Valid @RequestBody IncidentUpdateRequest request,
                                   Authentication auth) {
        return commandService.update(id, request, auth.getName());
    }

    @PutMapping("/{id}/classify")
    @PreAuthorize("hasAnyAuthority('ROLE_DISPATCHER','ROLE_SENIOR_DISPATCHER')")
    @Operation(summary = "Классифицировать происшествие (тип и приоритет)")
    public IncidentResponse classify(@PathVariable UUID id,
                                     @Valid @RequestBody IncidentClassifyRequest request,
                                     Authentication auth) {
        return commandService.classify(id, request, auth.getName());
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyAuthority('ROLE_DISPATCHER','ROLE_SENIOR_DISPATCHER')")
    @Operation(summary = "Изменить статус карточки")
    public IncidentResponse changeStatus(@PathVariable UUID id,
                                         @Valid @RequestBody IncidentStatusChangeRequest request,
                                         Authentication auth) {
        return commandService.changeStatus(id, request, auth.getName());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Карточка по идентификатору")
    public IncidentResponse getById(@PathVariable UUID id) {
        return queryService.getById(id);
    }

    @GetMapping("/{id}/history")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "История изменений карточки")
    public List<IncidentHistoryResponse> getHistory(@PathVariable UUID id) {
        return queryService.getHistory(id);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Поиск карточек с фильтрами и пагинацией")
    public Page<IncidentResponse> search(@ModelAttribute IncidentFilter filter,
                                         @PageableDefault(size = 20, sort = "createdAt",
                                             direction = Sort.Direction.DESC) Pageable pageable) {
        return queryService.search(filter, pageable);
    }

    @GetMapping("/active")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Все активные происшествия (для карты)")
    public List<IncidentResponse> getActive() {
        return queryService.getActive();
    }

    @GetMapping("/stats")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Операционная статистика (кэш Redis)")
    public IncidentStatsResponse getStats() {
        return queryService.getStats();
    }
}
