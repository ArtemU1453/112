package by.mchs.e112.platform.sdk;

/**
 * Метаданные SDK и перечень предоставляемых модулей (Internal SDK Governance Standard).
 * Каждый новый сервис обязан подключать SDK и использовать единые библиотеки платформы
 * (Service Maturity Model — уровень базовой зрелости).
 */
public final class PlatformSdk {

    public static final String VERSION = "1.0.0";

    private PlatformSdk() {
    }

    /** Обязательные модули, предоставляемые SDK любому сервису. */
    public static PlatformModule[] providedModules() {
        return PlatformModule.values();
    }
}
