package by.mchs.e112.dispatch.exception;

import by.mchs.e112.dispatch.domain.UnitStatus;

public class IllegalUnitStatusTransitionException extends RuntimeException {
    public IllegalUnitStatusTransitionException(UnitStatus from, UnitStatus to) {
        super("Недопустимый переход статуса подразделения: %s -> %s".formatted(from, to));
    }
}
