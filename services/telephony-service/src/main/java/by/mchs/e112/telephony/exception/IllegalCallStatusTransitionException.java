package by.mchs.e112.telephony.exception;

import by.mchs.e112.telephony.domain.CallStatus;

public class IllegalCallStatusTransitionException extends RuntimeException {
    public IllegalCallStatusTransitionException(CallStatus from, CallStatus to) {
        super("Недопустимый переход статуса вызова: %s -> %s".formatted(from, to));
    }
}
