package org.ksa.exception;

/**
 * Exception thrown when the provided SSH key value is invalid.
 */
public class InvalidSshKeyException extends RuntimeException {

    /**
     * Constructor for {@code InvalidSshKeyException}.
     *
     * @param message detail message for the exception
     */
    public InvalidSshKeyException(String message) {
        super(message);
    }
}
