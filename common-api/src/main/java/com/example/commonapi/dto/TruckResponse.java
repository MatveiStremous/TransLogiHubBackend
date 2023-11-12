package com.example.commonapi.dto;

import com.example.commonapi.model.enums.TransportStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Year;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TruckResponse {
    private Integer id;
    private String stateNumber;
    private Integer mileage;
    private Year yearOfIssue;
    private Integer maxCargoWeight;
    private TransportStatus status;
    private Integer weight;
    private String note;
    private Boolean isActive;
}
