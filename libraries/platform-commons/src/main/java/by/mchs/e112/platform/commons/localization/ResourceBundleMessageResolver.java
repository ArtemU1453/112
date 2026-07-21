package by.mchs.e112.platform.commons.localization;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Реализация {@link MessageResolver} поверх {@link ResourceBundle}. Базовое имя бандла задаётся
 * при создании; при отсутствии ключа возвращается сам ключ (безопасное поведение).
 */
public final class ResourceBundleMessageResolver implements MessageResolver {

    private final String baseName;

    public ResourceBundleMessageResolver(String baseName) {
        this.baseName = baseName;
    }

    @Override
    public String resolve(String key, Locale locale, Object... args) {
        Locale effective = locale == null ? SupportedLocales.DEFAULT : locale;
        try {
            String pattern = ResourceBundle.getBundle(baseName, effective).getString(key);
            return args == null || args.length == 0 ? pattern : MessageFormat.format(pattern, args);
        } catch (MissingResourceException ex) {
            return key;
        }
    }
}
