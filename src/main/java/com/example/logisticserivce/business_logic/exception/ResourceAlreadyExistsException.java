package com.example.logisticserivce.business_logic.exception;

import lombok.Data;

@Data

public class ResourceAlreadyExistsException extends ServiceException {
    private static final long serialVersionUID = 1L;

    public ResourceAlreadyExistsException(String message, Throwable cause, boolean enableSuppression,
                                          boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ResourceAlreadyExistsException() {
        super();
    }

    public ResourceAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceAlreadyExistsException(String message, String messageKey, String name) {
        super(message, messageKey, name);
    }

    public ResourceAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
