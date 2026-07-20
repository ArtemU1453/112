package by.mchs.e112.telephony.web;

import by.mchs.e112.telephony.dto.CallCompleteRequest;
import by.mchs.e112.telephony.dto.CallLinkIncidentRequest;
import by.mchs.e112.telephony.dto.CallResponse;
import by.mchs.e112.telephony.dto.CallStartRequest;
import by.mchs.e112.telephony.service.CallService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/calls")
@Tag(name = "Вызовы", description = "Приём и обработка экстренных вызовов 112")
public class CallController {

    private final CallService callService;

    public CallController(CallService callService) {
        this.callService = callService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ROLE_DISPATCHER','ROLE_SENIOR_DISPATCHER')")
    @Operation(summary = "Зарегистрировать поступивший вызов")
    public CallResponse start(@Valid @RequestBody CallStartRequest request) {
        return callService.start(request);
    }

    @PutMapping("/{id}/answer")
    @PreAuthorize("hasAnyAuthority('ROLE_DISPATCHER','ROLE_SENIOR_DISPATCHER')")
    @Operation(summary = "Ответить на вызов")
    public CallResponse answer(@PathVariable UUID id, Authentication auth) {
        return callService.answer(id, auth.getName());
    }

    @PutMapping("/{id}/hold")
    @PreAuthorize("hasAnyAuthority('ROLE_DISPATCHER','ROLE_SENIOR_DISPATCHER')")
    @Operation(summary = "Поставить вызов на удержание")
    public CallResponse hold(@PathVariable UUID id) {
        return callService.hold(id);
    }

    @PutMapping("/{id}/resume")
    @PreAuthorize("hasAnyAuthority('ROLE_DISPATCHER','ROLE_SENIOR_DISPATCHER')")
    @Operation(summary = "Снять вызов с удержания")
    public CallResponse resume(@PathVariable UUID id) {
        return callService.resume(id);
    }

    @PutMapping("/{id}/complete")
    @PreAuthorize("hasAnyAuthority('ROLE_DISPATCHER','ROLE_SENIOR_DISPATCHER')")
    @Operation(summary = "Завершить вызов (публикует call.received для ИИ-анализа)")
    public CallResponse complete(@PathVariable UUID id, @Valid @RequestBody CallCompleteRequest request) {
        return callService.complete(id, request);
    }

    @PutMapping("/{id}/miss")
    @PreAuthorize("hasAnyAuthority('ROLE_DISPATCHER','ROLE_SENIOR_DISPATCHER')")
    @Operation(summary = "Отметить пропущенный вызов")
    public CallResponse miss(@PathVariable UUID id) {
        return callService.miss(id);
    }

    @PutMapping("/{id}/incident")
    @PreAuthorize("hasAnyAuthority('ROLE_DISPATCHER','ROLE_SENIOR_DISPATCHER')")
    @Operation(summary = "Привязать вызов к карточке происшествия")
    public CallResponse linkIncident(@PathVariable UUID id, @Valid @RequestBody CallLinkIncidentRequest request) {
        return callService.linkIncident(id, request.incidentId());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Вызов по идентификатору")
    public CallResponse getById(@PathVariable UUID id) {
        return callService.getById(id);
    }

    @GetMapping("/active")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Активные вызовы на линии")
    public List<CallResponse> getActive() {
        return callService.getActive();
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "История вызовов абонента")
    public List<CallResponse> getByCaller(@RequestParam String callerPhone) {
        return callService.getByCaller(callerPhone);
    }
}
