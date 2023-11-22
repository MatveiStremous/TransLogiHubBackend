package com.example.commonservice.dto;

import com.example.commonservice.model.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderResponse {
    private Integer id;
    private String cargoName;
    private Integer totalWeight;
    private Integer numberOfTrucks;
    private String departureAddress;
    private String arrivalAddress;
    private LocalDateTime loadingDate;
    private LocalDateTime unloadingDate;
    private OrderStatus status;
    private String note;
}
