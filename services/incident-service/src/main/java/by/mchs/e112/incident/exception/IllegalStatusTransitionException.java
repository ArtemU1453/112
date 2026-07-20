package by.mchs.e112.incident.exception;

import by.mchs.e112.incident.domain.IncidentStatus;

public class IllegalStatusTransitionException extends RuntimeException {

    public IllegalStatusTransitionException(IncidentStatus from, IncidentStatus to) {
        super("Недопустимый переход статуса: %s -> %s".formatted(from, to));
    }
}
