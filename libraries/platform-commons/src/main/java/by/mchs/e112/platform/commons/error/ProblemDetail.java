package by.mchs.e112.platform.commons.error;

import java.time.Instant;
import java.util.List;

/**
 * Единый формат ошибки согласно RFC 7807 (application/problem+json).
 * Не содержит внутренних деталей/стека (безопасное отображение, Security Standards).
 *
 * @param type          URI-идентификатор типа проблемы
 * @param title         краткий заголовок
 * @param status        HTTP-статус
 * @param code          стабильный код ошибки (ErrorCode)
 * @param detail        локализованное описание
 * @param instance      идентификатор запроса/ресурса
 * @param correlationId сквозной идентификатор для трассировки (Observability)
 * @param timestamp     момент формирования (UTC)
 * @param violations    нарушения валидации (может быть пустым)
 */
public record ProblemDetail(
        String type,
        String title,
        int status,
        String code,
        String detail,
        String instance,
        String correlationId,
        Instant timestamp,
        List<FieldViolation> violations) {

    public ProblemDetail {
        violations = violations == null ? List.of() : List.copyOf(violations);
    }
}
