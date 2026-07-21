package by.mchs.e112.platform.abstractions.messaging;

import java.time.Duration;

/**
 * Политика повторной обработки (Messaging Platform: Retry Policy). Экспоненциальная задержка с
 * ограничением числа попыток; при исчерпании — перенос в DLQ (Dead Letter Queue).
 *
 * @param maxAttempts    максимум попыток (>=1)
 * @param initialBackoff начальная задержка
 * @param multiplier     множитель экспоненты
 * @param maxBackoff     верхняя граница задержки
 */
public record RetryPolicy(int maxAttempts, Duration initialBackoff, double multiplier, Duration maxBackoff) {

    public RetryPolicy {
        if (maxAttempts < 1) {
            throw new IllegalArgumentException("maxAttempts must be >= 1");
        }
    }

    public static RetryPolicy defaults() {
        return new RetryPolicy(5, Duration.ofSeconds(1), 2.0, Duration.ofMinutes(1));
    }

    /** Задержка перед попыткой номер {@code attempt} (1-based). */
    public Duration backoffFor(int attempt) {
        double millis = initialBackoff.toMillis() * Math.pow(multiplier, Math.max(0, attempt - 1));
        long capped = Math.min((long) millis, maxBackoff.toMillis());
        return Duration.ofMillis(capped);
    }
}
