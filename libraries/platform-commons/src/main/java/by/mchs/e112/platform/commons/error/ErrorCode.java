package by.mchs.e112.platform.commons.error;

/**
 * Единый перечень кодов ошибок Core Platform (Error Handling Framework, Stage 4).
 * Каждый код соответствует HTTP-статусу и стабильному строковому идентификатору,
 * используемому в теле ошибки (RFC 7807) и для локализации сообщений.
 */
public enum ErrorCode {

    VALIDATION_FAILED("platform.validation.failed", 400),
    UNAUTHORIZED("platform.security.unauthorized", 401),
    FORBIDDEN("platform.security.forbidden", 403),
    NOT_FOUND("platform.resource.not_found", 404),
    CONFLICT("platform.resource.conflict", 409),
    UNPROCESSABLE("platform.request.unprocessable", 422),
    RATE_LIMITED("platform.request.rate_limited", 429),
    INTERNAL_ERROR("platform.internal.error", 500),
    DEPENDENCY_UNAVAILABLE("platform.dependency.unavailable", 503);

    private final String code;
    private final int httpStatus;

    ErrorCode(String code, int httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public String code() {
        return code;
    }

    public int httpStatus() {
        return httpStatus;
    }
}
