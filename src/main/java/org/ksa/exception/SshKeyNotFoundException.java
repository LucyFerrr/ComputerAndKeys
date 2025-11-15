package org.ksa.exception;

public class SshKeyNotFoundException extends RuntimeException {
    public SshKeyNotFoundException(String message) {
        super(message);
    }
}
