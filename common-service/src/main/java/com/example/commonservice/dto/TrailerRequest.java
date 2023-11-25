package com.example.commonservice.dto;

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
