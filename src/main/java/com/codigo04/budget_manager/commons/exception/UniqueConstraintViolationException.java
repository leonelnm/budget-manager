package com.codigo04.budget_manager.commons.exception;

public class UniqueConstraintViolationException extends RuntimeException {

    public UniqueConstraintViolationException(String message) {
        super(message);
    }
}
