package com.iks.commerce.returnorder.exception;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Custom method argument not valid exception.
 */
public final class CustomMethodArgumentNotValidException
        extends MethodArgumentNotValidException {

    /**
     * Instantiates a new Custom method argument not valid exception.
     *
     * @param parameter     the parameter
     * @param bindingResult the binding result
     */
    public CustomMethodArgumentNotValidException(
            final MethodParameter parameter,
            final BindingResult bindingResult) {
        super(parameter, bindingResult);
    }

    /**
     * Gets error details.
     *
     * @return the error details
     */
    // Custom method to extract error details in your preferred format
    public Map<String, String> getErrorDetails() {
        Map<String, String> errors = new HashMap<>();
        getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    /**
     * The type Global exception handler.
     */
    @ControllerAdvice
    public static final class GlobalExceptionHandler {

        /**
         * Handle custom validation exceptions response entity.
         *
         * @param ex the exception
         * @return the response entity
         */
        @ExceptionHandler(
                CustomMethodArgumentNotValidException.class)
        public ResponseEntity
                <Map<String, String>> handleCustomValidationExceptions(
                final CustomMethodArgumentNotValidException ex) {
            Map<String, String> errors = ex.getErrorDetails();
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
    }
}
