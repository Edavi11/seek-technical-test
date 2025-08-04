package com.seek.test.seek_test.exception;

/**
 * Exception thrown when the provided age doesn't match the calculated age from birth date
 */
public class AgeBirthDateMismatchException extends RuntimeException {
    
    public AgeBirthDateMismatchException(String message) {
        super(message);
    }
    
    public AgeBirthDateMismatchException(String message, Throwable cause) {
        super(message, cause);
    }
} 