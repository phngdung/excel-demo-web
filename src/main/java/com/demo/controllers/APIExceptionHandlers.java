package com.demo.controllers;

import com.demo.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class APIExceptionHandlers extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<?> handleException(CustomException e) {
        return new ResponseEntity<Object>(e.getMessage(), HttpStatus.valueOf(e.getCode()));
    }
}