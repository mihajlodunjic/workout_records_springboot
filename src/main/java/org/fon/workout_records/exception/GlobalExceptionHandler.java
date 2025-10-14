package org.fon.workout_records.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    // 400 - Bean Validation na @RequestBody DTO
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                 HttpServletRequest req) {
        List<ApiError.FieldError> fields = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(fe ->
                fields.add(new ApiError.FieldError(fe.getField(), fe.getDefaultMessage()))
        );
        ApiError body = build(HttpStatus.BAD_REQUEST, "Validation failed",
                "Request body has invalid fields.", req.getRequestURI(), fields);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // 400 - Bean Validation na @PathVariable / @RequestParam
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException ex,
                                                              HttpServletRequest req) {
        List<ApiError.FieldError> fields = new ArrayList<>();
        for (ConstraintViolation<?> cv : ex.getConstraintViolations()) {
            String path = cv.getPropertyPath() != null ? cv.getPropertyPath().toString() : "";
            fields.add(new ApiError.FieldError(path, cv.getMessage()));
        }
        ApiError body = build(HttpStatus.BAD_REQUEST, "Constraint violation",
                "Request parameters are invalid.", req.getRequestURI(), fields);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // 404 - JPA Entity not found
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFound(EntityNotFoundException ex, HttpServletRequest req) {
        ApiError body = build(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), req.getRequestURI(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    // 401/403 - security
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDenied(AccessDeniedException ex, HttpServletRequest req) {
        ApiError body = build(HttpStatus.FORBIDDEN, "Forbidden", "You do not have permission.", req.getRequestURI(), null);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }

    // ResponseStatusException i ErrorResponseException
    @ExceptionHandler({ ResponseStatusException.class, ErrorResponseException.class })
    public ResponseEntity<ApiError> handleResponseStatus(RuntimeException ex, HttpServletRequest req) {
        HttpStatus status;
        String message;

        if (ex instanceof ResponseStatusException rse) {
            status = HttpStatus.valueOf(rse.getStatusCode().value());
            message = rse.getReason();
        } else if (ex instanceof ErrorResponseException ere) {
            status = HttpStatus.valueOf(ere.getStatusCode().value());
            message = ere.getBody() != null ? ere.getBody().getDetail() : ere.getMessage();
        } else {
            status = HttpStatus.BAD_REQUEST;
            message = ex.getMessage();
        }

        ApiError body = build(status, status.getReasonPhrase(), message, req.getRequestURI(), null);
        return ResponseEntity.status(status).body(body);
    }

    // 409 - npr unique constraint (username)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrity(DataIntegrityViolationException ex, HttpServletRequest req) {
        ApiError body = build(HttpStatus.CONFLICT, "Data integrity violation",
                "Operation violates a database constraint.", req.getRequestURI(), null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    // 400 - IllegalArgumentException iz servisa
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest req) {
        ApiError body = build(HttpStatus.BAD_REQUEST, "Bad Request", ex.getMessage(), req.getRequestURI(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // 500 - fallback
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAll(Exception ex, HttpServletRequest req) {
        ApiError body = build(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error",
                "Unexpected error occurred.", req.getRequestURI(), null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    private ApiError build(HttpStatus status, String error, String message, String path,
                           List<ApiError.FieldError> fields) {
        return new ApiError(status.value(), error, message, path, fields);
    }
}
