package com.example.commonservice.controller;

import com.example.commonservice.dto.ErrorResponse;
import com.example.commonservice.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> businessException(BusinessException exception) {
        String errorMessage = messageSource.getMessage(exception.getErrorCode(), null, exception.getErrorCode(), null);
        return ResponseEntity
                .status(exception.getHttpStatus())
                .body(new ErrorResponse(exception.getErrorCode(), errorMessage));
    }
}
