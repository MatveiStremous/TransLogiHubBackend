package com.example.commonservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TransportationRequest {
    private Integer weight;
    private Integer finalDistance;
    private Integer spentFuel;
    private String note;
    private LocalDateTime dateOfLoading;
    private LocalDateTime dateOfUnloading;
    private Integer orderId;
    private Integer driverId;
    private Integer truckId;
    private Integer trailerId;
    private Integer convoyId;
}
