package org.ksa.exception;

public class InvalidSshKeyException extends RuntimeException{
    public InvalidSshKeyException(String message) {
        super(message);
    }
}
