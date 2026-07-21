package by.mchs.e112.platform.commons.security;

/**
 * Константы RBAC-ролей платформы (согласованы с realm Keycloak, ADR-007).
 * Значения ролей — единый источник для проверок доступа в сервисах.
 */
public final class Roles {

    public static final String DISPATCHER = "ROLE_DISPATCHER";
    public static final String SENIOR_DISPATCHER = "ROLE_SENIOR_DISPATCHER";
    public static final String ADMIN = "ROLE_ADMIN";
    public static final String CREW = "ROLE_CREW";
    public static final String ANALYST = "ROLE_ANALYST";

    private Roles() {
    }
}
