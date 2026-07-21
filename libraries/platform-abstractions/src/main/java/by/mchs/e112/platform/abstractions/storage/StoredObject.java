package by.mchs.e112.platform.abstractions.storage;

import java.time.Instant;

/**
 * Метаданные объекта в файловом хранилище (File Storage Abstraction).
 *
 * @param key         ключ объекта (путь)
 * @param contentType MIME-тип
 * @param sizeBytes   размер
 * @param checksum    контрольная сумма (целостность)
 * @param createdAt   момент создания (UTC)
 */
public record StoredObject(String key, String contentType, long sizeBytes, String checksum, Instant createdAt) {
}
