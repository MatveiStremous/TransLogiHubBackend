package com.example.commonservice.dto;

import com.example.commonservice.model.enums.OrderStatus;
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
    private String clientEmail;
}
