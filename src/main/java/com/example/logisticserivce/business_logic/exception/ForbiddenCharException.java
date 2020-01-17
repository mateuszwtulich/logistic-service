package com.example.logisticserivce.business_logic.exception;


import lombok.Data;

@Data
public class ForbiddenCharException extends ServiceException{
    private static final long serialVersionUID = 1L;

    public ForbiddenCharException(String message, Throwable cause, boolean enableSuppression,
                                  boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ForbiddenCharException() {
        super();
    }

    public ForbiddenCharException(String message, Throwable cause) {
        super(message, cause);
    }

    public ForbiddenCharException(String message, String messageKey, String name) {
        super(message, messageKey, name);
    }

    public ForbiddenCharException(Throwable cause) {
        super(cause);
    }
}
