package by.mchs.e112.dispatch.exception;

public class UnitNotAvailableException extends RuntimeException {
    public UnitNotAvailableException(String callSign) {
        super("Подразделение %s недоступно для назначения".formatted(callSign));
    }
}
