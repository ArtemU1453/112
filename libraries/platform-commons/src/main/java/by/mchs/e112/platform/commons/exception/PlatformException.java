package by.mchs.e112.platform.commons.exception;

import by.mchs.e112.platform.commons.error.ErrorCode;
import by.mchs.e112.platform.commons.error.FieldViolation;
import java.util.List;

/**
 * Базовое исключение Core Platform. Несёт {@link ErrorCode} и (опционально) нарушения валидации.
 * Транслируется в {@link by.mchs.e112.platform.commons.error.ProblemDetail} на границе сервиса.
 */
public class PlatformException extends RuntimeException {

    private final ErrorCode errorCode;
    private final transient List<FieldViolation> violations;

    public PlatformException(ErrorCode errorCode, String message) {
        this(errorCode, message, List.of(), null);
    }

    public PlatformException(ErrorCode errorCode, String message, Throwable cause) {
        this(errorCode, message, List.of(), cause);
    }

    public PlatformException(ErrorCode errorCode, String message, List<FieldViolation> violations, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.violations = violations == null ? List.of() : List.copyOf(violations);
    }

    public ErrorCode errorCode() {
        return errorCode;
    }

    public List<FieldViolation> violations() {
        return violations;
    }
}
