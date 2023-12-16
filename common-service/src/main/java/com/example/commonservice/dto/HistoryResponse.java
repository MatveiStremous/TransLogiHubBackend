package com.example.commonservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryResponse {
    private String message;
    private LocalDateTime dateTime;
    private HistoryUserResponse user;
}
