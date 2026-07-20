package by.mchs.e112.dispatch.web;

import by.mchs.e112.dispatch.exception.AssignmentNotFoundException;
import by.mchs.e112.dispatch.exception.ConflictException;
import by.mchs.e112.dispatch.exception.IllegalUnitStatusTransitionException;
import by.mchs.e112.dispatch.exception.NoAvailableUnitException;
import by.mchs.e112.dispatch.exception.UnitNotAvailableException;
import by.mchs.e112.dispatch.exception.UnitNotFoundException;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({UnitNotFoundException.class, AssignmentNotFoundException.class})
    public ProblemDetail handleNotFound(RuntimeException ex) {
        return problem(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({ConflictException.class, IllegalUnitStatusTransitionException.class,
        UnitNotAvailableException.class})
    public ProblemDetail handleConflict(RuntimeException ex) {
        return problem(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(NoAvailableUnitException.class)
    public ProblemDetail handleNoUnit(NoAvailableUnitException ex) {
        return problem(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ProblemDetail handleOptimisticLock(ObjectOptimisticLockingFailureException ex) {
        return problem(HttpStatus.CONFLICT, "Подразделение изменено параллельно, повторите операцию");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
        ProblemDetail detail = problem(HttpStatus.BAD_REQUEST, "Ошибка валидации запроса");
        Map<String, String> errors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
        detail.setProperty("errors", errors);
        return detail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneric(Exception ex) {
        log.error("Внутренняя ошибка dispatch-service", ex);
        return problem(HttpStatus.INTERNAL_SERVER_ERROR, "Внутренняя ошибка сервиса");
    }

    private ProblemDetail problem(HttpStatus status, String message) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(status, message);
        detail.setProperty("timestamp", Instant.now());
        return detail;
    }
}
