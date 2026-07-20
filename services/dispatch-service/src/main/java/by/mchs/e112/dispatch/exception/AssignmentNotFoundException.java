package by.mchs.e112.dispatch.exception;

import java.util.UUID;

public class AssignmentNotFoundException extends RuntimeException {
    public AssignmentNotFoundException(UUID id) {
        super("Назначение %s не найдено".formatted(id));
    }
}
