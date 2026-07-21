package by.mchs.e112.platform.commons.exception;

import by.mchs.e112.platform.commons.error.ErrorCode;

/** Ресурс не найден (HTTP 404). */
public class NotFoundException extends PlatformException {
    public NotFoundException(String message) {
        super(ErrorCode.NOT_FOUND, message);
    }
}
