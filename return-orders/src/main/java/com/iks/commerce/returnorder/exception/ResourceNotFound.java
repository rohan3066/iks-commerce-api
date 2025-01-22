package com.iks.commerce.returnorder.exception;

/**
 * The type Resource not found.
 */
public class ResourceNotFound extends RuntimeException {
    /**
     * Instantiates a new Resource not found.
     *
     * @param message the message
     */
    public ResourceNotFound(final String message) {
        super(message);
    }

    /**
     * Instantiates a new Resource not found.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ResourceNotFound(final String message, final Throwable cause) {
        super(message, cause);
    }
}
