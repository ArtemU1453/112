package by.mchs.e112.platform.commons.error;

import java.time.Instant;
import java.util.List;

/**
 * Фабрика {@link ProblemDetail}. Формирует безопасное тело ошибки из кода, описания и контекста.
 */
public final class ProblemDetails {

    private static final String TYPE_PREFIX = "https://docs.112.by/errors/";

    private ProblemDetails() {
    }

    public static ProblemDetail of(ErrorCode code, String detail, String instance, String correlationId) {
        return of(code, detail, instance, correlationId, List.of());
    }

    public static ProblemDetail of(ErrorCode code,
                                   String detail,
                                   String instance,
                                   String correlationId,
                                   List<FieldViolation> violations) {
        return new ProblemDetail(
                TYPE_PREFIX + code.code(),
                titleOf(code),
                code.httpStatus(),
                code.code(),
                detail,
                instance,
                correlationId,
                Instant.now(),
                violations);
    }

    private static String titleOf(ErrorCode code) {
        return switch (code) {
            case VALIDATION_FAILED -> "Validation failed";
            case UNAUTHORIZED -> "Unauthorized";
            case FORBIDDEN -> "Forbidden";
            case NOT_FOUND -> "Resource not found";
            case CONFLICT -> "Conflict";
            case UNPROCESSABLE -> "Unprocessable request";
            case RATE_LIMITED -> "Too many requests";
            case DEPENDENCY_UNAVAILABLE -> "Dependency unavailable";
            case INTERNAL_ERROR -> "Internal error";
        };
    }
}
