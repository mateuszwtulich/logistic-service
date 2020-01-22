package com.example.logisticserivce.business_logic.exception;

import lombok.Data;

@Data

public class DriverNotAvailableException extends ServiceException {
    private static final long serialVersionUID = 1L;

    public DriverNotAvailableException(String message, Throwable cause, boolean enableSuppression,
                                          boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public DriverNotAvailableException() {
        super();
    }

    public DriverNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public DriverNotAvailableException(String message, String messageKey, String name) {
        super(message, messageKey, name);
    }

    public DriverNotAvailableException(Throwable cause) {
        super(cause);
    }
}