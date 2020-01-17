package com.example.logisticserivce.business_logic.exception;

import lombok.Data;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Data
public class ValidationError extends RequestError{
    private static final String DEFAULT_MESSAGE = "Field validation error";
    private Map<String, String> fieldErrors = new HashMap<>();

    public ValidationError(int status, String message, String messageKey, String name) {
        super(status, message, messageKey, name);
    }

    public void addFieldError(String path, String message) {
        FieldError error = new FieldError(path, message, DEFAULT_MESSAGE);
        fieldErrors.put(error.getObjectName(), error.getField() );
    }
}
