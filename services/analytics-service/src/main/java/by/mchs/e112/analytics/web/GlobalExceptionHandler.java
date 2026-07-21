package by.mchs.e112.analytics.web;

import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.MissingServletRequestParameterException;

/**
 * Единая обработка ошибок в формате RFC 7807 (ProblemDetail). Ошибки валидации/типов параметров —
 * 400; прочее — 500. Заглушки и «глотание» исключений запрещены.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({IllegalArgumentException.class, MethodArgumentTypeMismatchException.class,
        MissingServletRequestParameterException.class})
    public ProblemDetail handleBadRequest(Exception ex) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST, ex.getMessage() != null ? ex.getMessage()
                : "Некорректные параметры запроса");
        detail.setProperty("timestamp", Instant.now());
        return detail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneric(Exception ex) {
        log.error("Внутренняя ошибка analytics-service", ex);
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(
            HttpStatus.INTERNAL_SERVER_ERROR, "Внутренняя ошибка сервиса");
        detail.setProperty("timestamp", Instant.now());
        return detail;
    }
}
