package by.mchs.e112.platform.commons.validation;

import by.mchs.e112.platform.commons.error.FieldViolation;
import by.mchs.e112.platform.commons.exception.ValidationException;
import java.util.ArrayList;
import java.util.List;

/**
 * Накопитель результатов валидации (Validation Framework). Собирает нарушения и, при наличии,
 * бросает {@link ValidationException} с полным перечнем (не «первая ошибка»).
 */
public final class ValidationResult {

    private final List<FieldViolation> violations = new ArrayList<>();

    public ValidationResult reject(String field, String messageKey, String message) {
        violations.add(new FieldViolation(field, messageKey, message));
        return this;
    }

    public ValidationResult rejectIf(boolean condition, String field, String messageKey, String message) {
        if (condition) {
            reject(field, messageKey, message);
        }
        return this;
    }

    public boolean hasErrors() {
        return !violations.isEmpty();
    }

    public List<FieldViolation> violations() {
        return List.copyOf(violations);
    }

    public void throwIfInvalid(String message) {
        if (hasErrors()) {
            throw new ValidationException(message, violations());
        }
    }
}
