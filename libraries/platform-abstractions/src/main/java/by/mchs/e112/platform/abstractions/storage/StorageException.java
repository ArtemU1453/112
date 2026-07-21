package by.mchs.e112.platform.abstractions.storage;

import by.mchs.e112.platform.commons.error.ErrorCode;
import by.mchs.e112.platform.commons.exception.PlatformException;

/** Ошибка операции с файловым хранилищем. */
public class StorageException extends PlatformException {
    public StorageException(String message, Throwable cause) {
        super(ErrorCode.DEPENDENCY_UNAVAILABLE, message, cause);
    }
}
