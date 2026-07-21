package by.mchs.e112.platform.commons.localization;

import java.util.List;
import java.util.Locale;

/** Поддерживаемые локали интерфейса и сообщений (Localization Platform). */
public final class SupportedLocales {

    public static final Locale RU = Locale.forLanguageTag("ru");
    public static final Locale BE = Locale.forLanguageTag("be");
    public static final Locale EN = Locale.forLanguageTag("en");

    public static final Locale DEFAULT = RU;

    public static final List<Locale> ALL = List.of(RU, BE, EN);

    private SupportedLocales() {
    }

    public static Locale resolveOrDefault(String languageTag) {
        if (languageTag == null || languageTag.isBlank()) {
            return DEFAULT;
        }
        Locale requested = Locale.forLanguageTag(languageTag);
        return ALL.stream()
                .filter(l -> l.getLanguage().equals(requested.getLanguage()))
                .findFirst()
                .orElse(DEFAULT);
    }
}
