package core.bankmini.flightservice.utility;

import core.bankmini.flightservice.exceptions.AirportAlreadyExistsException;
import core.bankmini.flightservice.exceptions.FlightAlreadyExistException;
import core.bankmini.flightservice.model.dto.ValidationErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Общий билдер ошибки (чтобы не копипастить)
    private ProblemDetail buildProblem(
            HttpStatus status,
            String title,
            String detail,
            HttpServletRequest request
    ) {
        ProblemDetail problem = ProblemDetail.forStatus(status);
        problem.setTitle(title);
        problem.setDetail(detail);
        problem.setProperty("timestamp", LocalDateTime.now());
        problem.setProperty("path", request.getRequestURI());
        return problem;
    }

    // 400 (ручные ошибки)
    @ExceptionHandler(NoSuchElementException.class)
    public ProblemDetail handleBadRequest(IllegalArgumentException ex, HttpServletRequest request) {
        return buildProblem(HttpStatus.BAD_REQUEST, "Bad request", ex.getMessage(), request);
    }

    // 400 (валидация параметров)
    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraint(ConstraintViolationException ex, HttpServletRequest request) {
        return buildProblem(HttpStatus.BAD_REQUEST, "Constraint violation", ex.getMessage(), request);
    }

    // 400 (битый JSON)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleJson(HttpMessageNotReadableException ex, HttpServletRequest request) {
        return buildProblem(HttpStatus.BAD_REQUEST, "Malformed JSON", ex.getMostSpecificCause().getMessage(), request);
    }


    //400 ошибка валидации
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleBusinessExByNullOrBlankFields(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errors = new ConcurrentHashMap<>() {
        };

        ex.getBindingResult().getFieldErrors()
                .forEach(err -> {
                    String field = err.getField();
                    String message = err.getDefaultMessage();
                    errors.put(field, message);
                });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ValidationErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Validation failed")
                        .path(request.getRequestURI())
                        .errors(errors)
                        .build()
        );
    }

    // 404 (не найден endpoint)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ProblemDetail handleNoHandler(NoHandlerFoundException ex, HttpServletRequest request) {
        return buildProblem(HttpStatus.NOT_FOUND, "Endpoint not found", ex.getMessage(), request);
    }

    // 404 (не найден предмет)
    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleNoArgument(IllegalArgumentException ex, HttpServletRequest request) {
        return buildProblem(HttpStatus.NOT_FOUND, "Not found", ex.getMessage(), request);
    }

    // 405 (не тот HTTP метод)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ProblemDetail handleMethod(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        return buildProblem(HttpStatus.METHOD_NOT_ALLOWED, "Method not allowed", ex.getMessage(), request);
    }

    // 409 (Airport уже существует)
    @ExceptionHandler(AirportAlreadyExistsException.class)
    public ProblemDetail handleMethodAirportAlreadyExists(AirportAlreadyExistsException ex, HttpServletRequest request) {
        return buildProblem(HttpStatus.CONFLICT, "Airport already exists", ex.getMessage(), request);
    }

    // 409 (Flight уже существует)
    @ExceptionHandler(FlightAlreadyExistException.class)
    public ProblemDetail handleMethodFlightAlreadyExists(FlightAlreadyExistException ex, HttpServletRequest request) {
        return buildProblem(HttpStatus.CONFLICT, "Flight already exists", ex.getMessage(), request);
    }

    // 500 (БД / JPA)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleServerConflict(DataIntegrityViolationException ex, HttpServletRequest request) {
        return buildProblem(HttpStatus.CONFLICT, "Database conflict", ex.getMostSpecificCause().getMessage(), request);
    }

    //  fallback
    @ExceptionHandler({
            Exception.class,
            NullPointerException.class,
    })
    public ProblemDetail handleOther(Exception ex, HttpServletRequest request) {

        log.error("Unhandled exception", ex); // обязательно лог

        return buildProblem(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal error",
                "Unexpected error",
                request
        );
    }

}
