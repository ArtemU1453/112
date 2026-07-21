package by.mchs.e112.platform.commons.dto;

/**
 * Универсальная обёртка успешного ответа с метаданными трассировки (опционально).
 * Ошибки представляются отдельно через ProblemDetail (Error Handling Framework).
 *
 * @param data          полезная нагрузка
 * @param correlationId сквозной идентификатор запроса
 * @param <T>           тип данных
 */
public record ApiResult<T>(T data, String correlationId) {
    public static <T> ApiResult<T> of(T data, String correlationId) {
        return new ApiResult<>(data, correlationId);
    }
}
