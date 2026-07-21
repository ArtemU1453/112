package by.mchs.e112.platform.sdk;

/**
 * Обязательные возможности, которые предоставляет SDK каждому сервису (Internal SDK Governance
 * Standard, Platform SDK). Служит контрольным перечнем интеграции сервиса с платформой.
 */
public enum PlatformModule {
    SECURITY,
    LOGGING,
    CONFIGURATION,
    AUDIT,
    ERROR_HANDLING,
    MONITORING,
    TRACING
}
