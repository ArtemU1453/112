package by.mchs.e112.gis.exception;

public class AddressNotFoundException extends RuntimeException {
    public AddressNotFoundException(String query) {
        super("Адрес не найден: %s".formatted(query));
    }
}
