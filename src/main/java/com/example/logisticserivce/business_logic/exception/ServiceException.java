package com.example.logisticserivce.business_logic.exception;

import lombok.Data;

@Data
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String messageKey;
    private String name;

    public ServiceException(String message, Throwable cause, boolean enableSuppression,
                         boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ServiceException() {
        super();
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(String message, String messageKey, String name) {
        super(message);
        this.messageKey = messageKey;
        this.name = name;
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}