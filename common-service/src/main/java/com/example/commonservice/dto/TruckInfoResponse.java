package com.example.commonservice.dto;

import com.example.commonservice.model.enums.TransportStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Year;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TruckInfoResponse {
    private Integer id;
    private String stateNumber;
    private String brand;
    private String model;
    private Integer mileage;
    private Year yearOfIssue;
    private Integer maxCargoWeight;
    private TransportStatus status;
    private Integer weight;
    private String note;
    private Boolean isActive;
    private ConvoyResponse convoy;
}
