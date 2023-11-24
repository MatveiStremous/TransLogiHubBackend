package com.example.commonservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrailerRequest {
    private String stateNumber;
    private String brand;
    private String model;
    private Integer maxCargoWeight;
    private Integer weight;
    private String note;
    private Integer typeId;
}
