package by.mchs.e112.platform.abstractions.messaging;

/**
 * Обработчик сообщения (команды/события). Реализация обязана быть идемпотентной
 * (Messaging Platform / Event Governance Standard).
 */
public interface MessageHandler {

    boolean canHandle(String messageType);

    void handle(Message message);
}
