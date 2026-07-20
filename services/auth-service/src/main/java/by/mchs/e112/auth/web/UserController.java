package by.mchs.e112.auth.web;

import by.mchs.e112.auth.dto.RoleAssignRequest;
import by.mchs.e112.auth.dto.UserCreateRequest;
import by.mchs.e112.auth.dto.UserResponse;
import by.mchs.e112.auth.dto.UserUpdateRequest;
import by.mchs.e112.auth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Пользователи", description = "Управление сотрудниками службы 112")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Создать пользователя (Keycloak + профиль)")
    public UserResponse create(@Valid @RequestBody UserCreateRequest request, Authentication auth) {
        return userService.create(request, auth.getName());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Обновить профиль пользователя")
    public UserResponse update(@PathVariable UUID id, @Valid @RequestBody UserUpdateRequest request,
                               Authentication auth) {
        return userService.update(id, request, auth.getName());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SENIOR_DISPATCHER','ROLE_ANALYST')")
    @Operation(summary = "Получить пользователя по идентификатору")
    public UserResponse getById(@PathVariable UUID id) {
        return userService.getById(id);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SENIOR_DISPATCHER','ROLE_ANALYST')")
    @Operation(summary = "Список всех пользователей")
    public List<UserResponse> getAll() {
        return userService.getAll();
    }

    @PutMapping("/{id}/roles")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Назначить роли (полная замена)")
    public void assignRoles(@PathVariable UUID id, @Valid @RequestBody RoleAssignRequest request,
                            Authentication auth) {
        userService.assignRoles(id, request, auth.getName());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Заблокировать пользователя")
    public void block(@PathVariable UUID id, Authentication auth) {
        userService.block(id, auth.getName());
    }

    @PostMapping("/{id}/activate")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Разблокировать пользователя")
    public void activate(@PathVariable UUID id, Authentication auth) {
        userService.activate(id, auth.getName());
    }
}
