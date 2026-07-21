package by.mchs.e112.platform.commons.logging;

import org.slf4j.MDC;
import by.mchs.e112.platform.commons.id.Ids;

/**
 * Управление сквозным корреляционным идентификатором в MDC (Logging Standards).
 * Идентификатор присваивается на периметре и пробрасывается по цепочке обработки.
 */
public final class CorrelationContext {

    private CorrelationContext() {
    }

    /** Возвращает текущий correlationId или создаёт новый, если отсутствует. */
    public static String getOrCreate() {
        String current = MDC.get(LogFields.CORRELATION_ID);
        if (current == null || current.isBlank()) {
            current = Ids.newCorrelationId();
            MDC.put(LogFields.CORRELATION_ID, current);
        }
        return current;
    }

    public static void set(String correlationId) {
        if (correlationId != null && !correlationId.isBlank()) {
            MDC.put(LogFields.CORRELATION_ID, correlationId);
        }
    }

    public static String get() {
        return MDC.get(LogFields.CORRELATION_ID);
    }

    public static void clear() {
        MDC.remove(LogFields.CORRELATION_ID);
        MDC.remove(LogFields.TRACE_ID);
        MDC.remove(LogFields.SPAN_ID);
        MDC.remove(LogFields.USER_ID);
    }
}
