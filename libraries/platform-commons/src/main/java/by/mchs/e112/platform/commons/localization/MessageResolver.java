package by.mchs.e112.platform.commons.localization;

import java.util.Locale;

/**
 * Разрешение локализованных сообщений по ключу (Localization Platform).
 * Используется Error Handling Framework для локализации сообщений об ошибках.
 */
public interface MessageResolver {

    String resolve(String key, Locale locale, Object... args);

    default String resolve(String key, Locale locale) {
        return resolve(key, locale, new Object[0]);
    }
}
