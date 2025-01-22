package com.iks.commerce.product_category.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * The type Global exception handler.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle illegal argument exception response entity.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String>
    handleIllegalArgumentException(
            final IllegalArgumentException e) {
        return new ResponseEntity<>(
                e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle general exception response entity.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String>
    handleGeneralException(final Exception e) {
        return new ResponseEntity<>(
                "An error occurred: " + e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
