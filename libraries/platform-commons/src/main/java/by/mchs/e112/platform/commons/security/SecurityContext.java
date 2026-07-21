package by.mchs.e112.platform.commons.security;

import by.mchs.e112.platform.commons.exception.ForbiddenException;
import by.mchs.e112.platform.commons.exception.UnauthorizedException;
import java.util.Optional;

/**
 * Доступ к текущему субъекту безопасности (Identity/Authorization). Реализация связывается
 * с механизмом аутентификации сервиса (например, Spring Security) через SDK.
 */
public interface SecurityContext {

    Optional<PlatformPrincipal> currentPrincipal();

    default PlatformPrincipal requirePrincipal() {
        return currentPrincipal()
                .orElseThrow(() -> new UnauthorizedException("Authentication required"));
    }

    default void requireRole(String role) {
        if (!requirePrincipal().hasRole(role)) {
            throw new ForbiddenException("Missing required role: " + role);
        }
    }
}
