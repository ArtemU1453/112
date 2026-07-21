package by.mchs.e112.platform.commons.exception;

import by.mchs.e112.platform.commons.error.ErrorCode;

/** Недостаточно прав (RBAC/ABAC, HTTP 403). */
public class ForbiddenException extends PlatformException {
    public ForbiddenException(String message) {
        super(ErrorCode.FORBIDDEN, message);
    }
}
