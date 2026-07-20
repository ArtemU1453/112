package by.mchs.e112.dispatch.web;

import by.mchs.e112.dispatch.dto.AssignmentRequest;
import by.mchs.e112.dispatch.dto.AssignmentResponse;
import by.mchs.e112.dispatch.dto.AutoDispatchRequest;
import by.mchs.e112.dispatch.dto.UnitCreateRequest;
import by.mchs.e112.dispatch.dto.UnitLocationUpdateRequest;
import by.mchs.e112.dispatch.dto.UnitResponse;
import by.mchs.e112.dispatch.dto.UnitStatusChangeRequest;
import by.mchs.e112.dispatch.service.DispatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Диспетчеризация", description = "Подразделения и назначения нарядов")
public class DispatchController {

    private final DispatchService dispatchService;

    public DispatchController(DispatchService dispatchService) {
        this.dispatchService = dispatchService;
    }

    @PostMapping("/units")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SENIOR_DISPATCHER')")
    @Operation(summary = "Зарегистрировать подразделение")
    public UnitResponse createUnit(@Valid @RequestBody UnitCreateRequest request, Authentication auth) {
        return dispatchService.createUnit(request, auth.getName());
    }

    @GetMapping("/units")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Список всех подразделений")
    public List<UnitResponse> getAllUnits() {
        return dispatchService.getAllUnits();
    }

    @GetMapping("/units/available")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Свободные подразделения")
    public List<UnitResponse> getAvailableUnits() {
        return dispatchService.getAvailableUnits();
    }

    @GetMapping("/units/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Подразделение по идентификатору")
    public UnitResponse getUnit(@PathVariable UUID id) {
        return dispatchService.getUnit(id);
    }

    @PutMapping("/units/{id}/status")
    @PreAuthorize("hasAnyAuthority('ROLE_DISPATCHER','ROLE_SENIOR_DISPATCHER','ROLE_CREW')")
    @Operation(summary = "Изменить оперативный статус подразделения")
    public UnitResponse changeUnitStatus(@PathVariable UUID id,
                                         @Valid @RequestBody UnitStatusChangeRequest request,
                                         Authentication auth) {
        return dispatchService.changeUnitStatus(id, request, auth.getName());
    }

    @PutMapping("/units/{id}/location")
    @PreAuthorize("hasAnyAuthority('ROLE_CREW','ROLE_DISPATCHER','ROLE_SENIOR_DISPATCHER')")
    @Operation(summary = "Обновить местоположение подразделения (телеметрия экипажа)")
    public UnitResponse updateLocation(@PathVariable UUID id,
                                       @Valid @RequestBody UnitLocationUpdateRequest request,
                                       Authentication auth) {
        return dispatchService.updateUnitLocation(id, request, auth.getName());
    }

    @PostMapping("/assignments")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ROLE_DISPATCHER','ROLE_SENIOR_DISPATCHER')")
    @Operation(summary = "Назначить конкретное подразделение на происшествие")
    public AssignmentResponse assign(@Valid @RequestBody AssignmentRequest request, Authentication auth) {
        return dispatchService.assign(request, auth.getName());
    }

    @PostMapping("/assignments/auto")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ROLE_DISPATCHER','ROLE_SENIOR_DISPATCHER')")
    @Operation(summary = "Автоматически назначить ближайшее свободное подразделение")
    public AssignmentResponse autoDispatch(@Valid @RequestBody AutoDispatchRequest request, Authentication auth) {
        return dispatchService.autoDispatch(request, auth.getName());
    }

    @PutMapping("/assignments/{id}/complete")
    @PreAuthorize("hasAnyAuthority('ROLE_DISPATCHER','ROLE_SENIOR_DISPATCHER','ROLE_CREW')")
    @Operation(summary = "Завершить назначение")
    public AssignmentResponse complete(@PathVariable UUID id, Authentication auth) {
        return dispatchService.completeAssignment(id, auth.getName());
    }

    @PutMapping("/assignments/{id}/recall")
    @PreAuthorize("hasAnyAuthority('ROLE_SENIOR_DISPATCHER')")
    @Operation(summary = "Отозвать наряд")
    public AssignmentResponse recall(@PathVariable UUID id, Authentication auth) {
        return dispatchService.recallAssignment(id, auth.getName());
    }

    @GetMapping("/incidents/{incidentId}/assignments")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Наряды, назначенные на происшествие")
    public List<AssignmentResponse> getByIncident(@PathVariable UUID incidentId) {
        return dispatchService.getAssignmentsByIncident(incidentId);
    }
}
