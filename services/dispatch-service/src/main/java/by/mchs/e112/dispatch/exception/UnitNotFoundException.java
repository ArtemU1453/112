package by.mchs.e112.dispatch.exception;

import java.util.UUID;

public class UnitNotFoundException extends RuntimeException {
    public UnitNotFoundException(UUID id) {
        super("Подразделение %s не найдено".formatted(id));
    }
}
