package by.mchs.e112.platform.commons.security;

import java.util.Set;
import java.util.UUID;

/**
 * Аутентифицированный субъект (Identity Platform). Иммутабельное представление личности и ролей,
 * извлечённых из JWT (Keycloak, ADR-007). Не содержит секретов/токенов.
 *
 * @param userId   идентификатор пользователя
 * @param username логин
 * @param roles    набор RBAC-ролей
 */
public record PlatformPrincipal(UUID userId, String username, Set<String> roles) {

    public PlatformPrincipal {
        roles = roles == null ? Set.of() : Set.copyOf(roles);
    }

    public boolean hasRole(String role) {
        return roles.contains(role);
    }

    public boolean hasAnyRole(String... candidates) {
        for (String role : candidates) {
            if (roles.contains(role)) {
                return true;
            }
        }
        return false;
    }
}
