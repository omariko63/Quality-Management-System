package com.example.quality_management_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex) {
        return new ResponseEntity<>(
                Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", ex.getStatusCode().value(),
                        "error", ex.getReason()
                ),
                ex.getStatusCode()
        );
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, Object>> handleNoSuchElementException(NoSuchElementException ex) {
        return new ResponseEntity<>(
                Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", HttpStatus.NOT_FOUND.value(),
                        "error", ex.getMessage()
                ),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalStateException(IllegalStateException ex) {
        return new ResponseEntity<>(
                Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", HttpStatus.BAD_REQUEST.value(),
                        "error", ex.getMessage()
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        return new ResponseEntity<>(
                Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "error", "An unexpected error occurred"
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
