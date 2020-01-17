package com.example.logisticserivce.controller;

import com.example.logisticserivce.business_logic.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {
    private ResponseEntity<Object> handler(ServiceException ex, WebRequest request, HttpStatus status) {
        RequestError bodyOfResponse = new RequestError(status.value(), ex.getMessage(), ex.getMessageKey(), ex.getName());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(value = { ResourceAlreadyExistsException.class})
    protected ResponseEntity<Object> handleConflict(ResourceAlreadyExistsException ex, WebRequest request) {
        return handler(ex, request, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleNotFound(ResourceNotFoundException ex, WebRequest request) {
        return handler(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ForbiddenCharException.class)
    protected ResponseEntity<Object> handleForbiddenChar(ForbiddenCharException ex, WebRequest request) {
        return handler(ex, request, HttpStatus.CONFLICT);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        ValidationError bodyOfResponse = processFieldErrors(fieldErrors);
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    private ValidationError processFieldErrors(List<FieldError> fieldErrors) {
        ValidationError error = new ValidationError(HttpStatus.BAD_REQUEST.value(), "validation error", "emptyField", "nonName");
        for (FieldError fieldError : fieldErrors) {
            error.addFieldError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return error;
    }
}

