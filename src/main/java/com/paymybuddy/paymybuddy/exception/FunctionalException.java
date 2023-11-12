package com.paymybuddy.paymybuddy.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class FunctionalException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String message;

    private final LocalDateTime timestamp;

    private HttpStatus status;

    private FunctionalException() {
        this.timestamp = LocalDateTime.now();
    }

    /**
     * This constructor is used for end-user error explanation
     */
    public FunctionalException(String message, HttpStatus status) {
        this();
        this.message = message;
        this.status = status;
    }

    /**
     * This constructor is used for developer explanation
     */
    public FunctionalException(HttpStatus status, Throwable cause) {
        this();
        this.message = cause.getMessage();
        this.status = status;
    }

    public FunctionalException(String message) {
        this();
        this.message = message;
    }

    public HttpStatus getStatus() {
        return this.status;
    }
}
