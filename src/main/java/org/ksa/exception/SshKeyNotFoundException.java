package org.ksa.exception;

/**
 * Exception thrown when the requested SSH key is not found.
 */
public class SshKeyNotFoundException extends RuntimeException {

    /**
     * Constructor for {@code SshKeyNotFoundException}.
     *
     * @param message detail message for the exception
     */
    public SshKeyNotFoundException(String message) {
        super(message);
    }
}
