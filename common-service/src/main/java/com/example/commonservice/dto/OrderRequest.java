package com.example.commonservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderRequest {
    private String cargoName;
    private Integer totalWeight;
    private Integer numberOfTrucks;
    private String departureAddress;
    private String arrivalAddress;
    private LocalDateTime loadingDate;
    private LocalDateTime unloadingDate;
    private String note;
}
