package org.berneick.exception;

public class InsufficientAvailableStockException extends RuntimeException {
    public InsufficientAvailableStockException(String message) {
        super(message);
    }
}
