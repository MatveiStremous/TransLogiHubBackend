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
public class OrderRequest {
    private String cargoName;
    private Integer totalWeight;
    private Integer numberOfTrucks;
    private String departureAddress;
    private String arrivalAddress;
    private LocalDateTime loadingDate;
    private LocalDateTime unloadingDate;
    private String note;
    private String clientEmail;
}
