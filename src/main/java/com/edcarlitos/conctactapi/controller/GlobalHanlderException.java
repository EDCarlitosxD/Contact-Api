package com.edcarlitos.conctactapi.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalHanlderException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handle(MethodArgumentNotValidException ex) {
        Map<String, List<String>> errors = new HashMap<>();

        errors.put("errors",
                ex.getBindingResult().getFieldErrors().stream()
                        .map(error -> error.getField() + " " +error.getDefaultMessage())
                        .collect(Collectors.toList()));
        return new ResponseEntity<>(errors,new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
