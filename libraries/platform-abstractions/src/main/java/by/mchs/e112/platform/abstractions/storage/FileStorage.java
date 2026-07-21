package by.mchs.e112.platform.abstractions.storage;

import java.io.InputStream;
import java.util.Optional;

/**
 * Абстракция файлового хранилища (File Storage Abstraction, Stage 4). Не привязана к поставщику
 * (S3-совместимое, ADR-013, или иной) — реализации подключаются адаптерами (TAP, CAP-STORAGE-001).
 * Поддерживает документы, аудиозаписи, вложения, экспортируемые файлы.
 */
public interface FileStorage {

    StoredObject put(String key, String contentType, InputStream data);

    Optional<InputStream> get(String key);

    Optional<StoredObject> stat(String key);

    boolean delete(String key);

    /** Временная ссылка для скачивания (если поддерживается реализацией). */
    Optional<String> presignedUrl(String key, long ttlSeconds);
}
