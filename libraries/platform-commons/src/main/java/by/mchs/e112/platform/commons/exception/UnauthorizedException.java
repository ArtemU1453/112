package by.mchs.e112.platform.commons.exception;

import by.mchs.e112.platform.commons.error.ErrorCode;

/** Не аутентифицирован (HTTP 401). */
public class UnauthorizedException extends PlatformException {
    public UnauthorizedException(String message) {
        super(ErrorCode.UNAUTHORIZED, message);
    }
}
