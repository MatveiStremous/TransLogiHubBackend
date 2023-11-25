package com.example.commonservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Year;

@Getter
@Setter
public class TrailerRequest {
    private String stateNumber;
    private String brand;
    private String model;
    private Year yearOfIssue;
    private Integer maxCargoWeight;
    private Integer weight;
    private String note;
    private Integer typeId;
}
