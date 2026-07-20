package by.mchs.e112.audit.config;

import org.springframework.context.annotation.Configuration;

/**
 * Маркер конфигурации; JSON-тип регистрируется аннотацией @Type на сущности AuditEvent
 * через hypersistence-utils, дополнительная настройка не требуется.
 */
@Configuration
public class HypersistenceConfig {
}
