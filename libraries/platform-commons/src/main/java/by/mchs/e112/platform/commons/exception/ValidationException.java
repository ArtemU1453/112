package by.mchs.e112.platform.commons.exception;

import by.mchs.e112.platform.commons.error.ErrorCode;
import by.mchs.e112.platform.commons.error.FieldViolation;
import java.util.List;

/** Ошибка валидации входных данных (HTTP 400) с перечнем нарушений полей. */
public class ValidationException extends PlatformException {
    public ValidationException(String message, List<FieldViolation> violations) {
        super(ErrorCode.VALIDATION_FAILED, message, violations, null);
    }
}
