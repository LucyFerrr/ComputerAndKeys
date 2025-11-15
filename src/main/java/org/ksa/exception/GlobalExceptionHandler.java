package org.ksa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * Global exception handler for REST controllers.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link ComputerNotFoundException} and returns a 404 Not Found response.
     *
     * @param ex exception thrown when computer is not found
     * @return a structured error response with status 404
     */
    @ExceptionHandler(ComputerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleComputerNotFound(ComputerNotFoundException ex) {
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error("Not Found")
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Handles {@link InvalidMakerException} and returns a 403 Forbidden response.
     *
     * @param ex exception thrown when maker is invalid
     * @return a structured error response with status 403
     */
    @ExceptionHandler(InvalidMakerException.class)
    public ResponseEntity<ErrorResponse> handleInvalidMaker(InvalidMakerException ex) {
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.FORBIDDEN.value())
                .error("Forbidden")
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    /**
     * Handles {@link InvalidSshKeyException} and returns a 400 Bad Request response.
     *
     * @param ex exception thrown when SSH key is invalid
     * @return a structured error response with status 400
     */
    @ExceptionHandler(InvalidSshKeyException.class)
    public ResponseEntity<ErrorResponse> handleInvalidSshKey(InvalidSshKeyException ex) {
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(("Bad Request"))
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
