package by.mchs.e112.platform.sdk;

import by.mchs.e112.platform.commons.security.SecurityContext;
import by.mchs.e112.platform.commons.time.TimeProvider;
import by.mchs.e112.platform.commons.localization.MessageResolver;

/**
 * Единая точка доступа к базовым возможностям платформы для сервиса (Platform SDK).
 * Реализация связывается конфигурацией сервиса (например, Spring), предоставляя единообразные
 * безопасность, время и локализацию. Не содержит бизнес-логики.
 */
public interface PlatformContext {

    SecurityContext security();

    TimeProvider time();

    MessageResolver messages();

    String serviceName();
}
