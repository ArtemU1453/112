package by.mchs.e112.telephony.exception;

import java.util.UUID;

public class CallNotFoundException extends RuntimeException {
    public CallNotFoundException(UUID id) {
        super("Вызов %s не найден".formatted(id));
    }
}
