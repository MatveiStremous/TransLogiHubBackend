package com.example.commonservice.controller;

import com.example.commonservice.dto.ErrorResponse;
import com.example.commonservice.exception.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> businessException(BusinessException exception) {
        return ResponseEntity
                .status(exception.getHttpStatus())
                .body(new ErrorResponse(exception.getMessage()));
    }
}
