package by.mchs.e112.platform.commons.validation;

import java.util.regex.Pattern;

/**
 * Набор переиспользуемых предикатов валидации общего назначения (Validation Framework).
 * Не содержит доменных правил (Constitution ARTICLE 3; запрет бизнес-логики Stage 4).
 */
public final class Validators {

    private static final Pattern EMAIL =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PHONE_BY =
            Pattern.compile("^\\+375\\d{9}$");

    private Validators() {
    }

    public static boolean notBlank(String value) {
        return value != null && !value.isBlank();
    }

    public static boolean lengthBetween(String value, int min, int max) {
        if (value == null) {
            return false;
        }
        int len = value.trim().length();
        return len >= min && len <= max;
    }

    public static boolean isEmail(String value) {
        return value != null && EMAIL.matcher(value).matches();
    }

    public static boolean isPhoneBy(String value) {
        return value != null && PHONE_BY.matcher(value).matches();
    }
}
