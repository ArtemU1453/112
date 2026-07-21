package by.mchs.e112.platform.commons.exception;

import by.mchs.e112.platform.commons.error.ErrorCode;

/** Конфликт состояния/инварианта или оптимистической блокировки (HTTP 409). */
public class ConflictException extends PlatformException {
    public ConflictException(String message) {
        super(ErrorCode.CONFLICT, message);
    }
}
