package by.mchs.e112.platform.abstractions.messaging;

/**
 * Хранилище ключей идемпотентности (Messaging Platform). Гарантирует однократную бизнес-обработку
 * при повторной доставке (at-least-once → эффективно once).
 */
public interface IdempotencyStore {

    /** Возвращает true, если сообщение с данным ключом ещё не обрабатывалось (и фиксирует ключ). */
    boolean registerIfAbsent(String messageId);

    boolean isProcessed(String messageId);
}
