package by.mchs.e112.platform.abstractions.messaging;

/**
 * Очередь недоставленных сообщений (Messaging Platform: DLQ). Сообщения, исчерпавшие RetryPolicy,
 * переносятся сюда для последующего разбора/повторной обработки.
 */
public interface DeadLetterQueue {

    void deadLetter(Message message, String reason, int attempts);
}
