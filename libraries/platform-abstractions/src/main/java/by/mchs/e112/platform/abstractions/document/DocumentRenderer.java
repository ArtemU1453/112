package by.mchs.e112.platform.abstractions.document;

/**
 * Порт генерации документов (Document Platform, Stage 4): шаблоны, генерация, экспорт, печать,
 * электронное хранение (совместно с File Storage Abstraction). Реализация — адаптером.
 */
public interface DocumentRenderer {

    boolean supports(DocumentFormat format);

    /** Возвращает сгенерированный документ как байтовый массив в заданном формате. */
    byte[] render(DocumentRequest request);
}
