package by.mchs.e112.incident.exception;

import java.util.UUID;

public class IncidentNotFoundException extends RuntimeException {

    public IncidentNotFoundException(UUID id) {
        super("Карточка происшествия %s не найдена".formatted(id));
    }
}
