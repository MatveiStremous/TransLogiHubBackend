package com.example.commonapi.dto;

import com.example.commonapi.model.enums.TransportStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.Year;

@Getter
@Setter
public class TruckRequest {
    private String stateNumber;
    private Integer mileage;
    private Year yearOfIssue;
    private Integer maxCargoWeight;
    private TransportStatus status;
    private Integer weight;
    private String note;
}
