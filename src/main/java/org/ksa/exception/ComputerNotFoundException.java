package org.ksa.exception;

/**
 * Exception thrown when requested computer record is not found.
 */
public class ComputerNotFoundException extends RuntimeException {

    /**
     * Constructor for {@code ComputerNotFoundException}.
     *
     * @param message detail message for the exception
     */
    public ComputerNotFoundException(String message) {
        super(message);
    }
}
