package com.example.commonservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private String backendCode;
    private long timestamp;

    public ErrorResponse(String backendCode, String userMessage) {
        this.message = userMessage;
        this.backendCode = backendCode;
        timestamp = System.currentTimeMillis();
    }
}