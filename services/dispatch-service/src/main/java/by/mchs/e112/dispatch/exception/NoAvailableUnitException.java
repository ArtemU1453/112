package by.mchs.e112.dispatch.exception;

public class NoAvailableUnitException extends RuntimeException {
    public NoAvailableUnitException(String type) {
        super("Нет доступных подразделений типа %s".formatted(type));
    }
}
