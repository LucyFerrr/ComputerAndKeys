package org.ksa.exception;

/**
 * Exception thrown when the provided maker value is invalid.
 */
public class InvalidMakerException extends RuntimeException {

    /**
     * Constructor for {@code InvalidMakerException}.
     *
     * @param message detail message for the exception
     */
    public InvalidMakerException(String message) {
        super(message);
    }
}
