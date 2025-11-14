package org.ksa.exception;

public class ComputerNotFoundException extends RuntimeException{
    public ComputerNotFoundException(String message) {
        super(message);
    }
}
