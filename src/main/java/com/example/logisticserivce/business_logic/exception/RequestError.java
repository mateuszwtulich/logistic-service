package com.example.logisticserivce.business_logic.exception;

import lombok.Data;

@Data
public class RequestError {
    protected final int status;
    protected final String message;
    protected final String messageKey;
    protected final String name;
}
