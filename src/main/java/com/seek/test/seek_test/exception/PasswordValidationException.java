package com.seek.test.seek_test.exception;

/**
 * Exception thrown when password validation fails
 */
public class PasswordValidationException extends RuntimeException {
    
    public PasswordValidationException(String message) {
        super(message);
    }
    
    public PasswordValidationException(String message, Throwable cause) {
        super(message, cause);
    }
} 