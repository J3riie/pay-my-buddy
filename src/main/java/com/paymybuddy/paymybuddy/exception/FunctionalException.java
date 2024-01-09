package com.paymybuddy.paymybuddy.exception;

import org.springframework.http.HttpStatus;

public class FunctionalException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String message;

    private final HttpStatus status;

    /**
     * This constructor is used for end-user error explanation
     */
    public FunctionalException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
