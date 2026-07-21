package by.mchs.e112.platform.commons.id;

import java.util.UUID;

/** Генерация идентификаторов (сущности, корреляция запросов) — Common Libraries: Utilities. */
public final class Ids {

    private Ids() {
    }

    /** Новый случайный идентификатор (UUID v4). */
    public static UUID newId() {
        return UUID.randomUUID();
    }

    /** Новый корреляционный идентификатор запроса (строка без дефисов). */
    public static String newCorrelationId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
