package com.example.commonservice.dto;

import com.example.commonservice.model.enums.TransportStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrailerRequest {
    private String stateNumber;
    private Integer maxCargoWeight;
    private TransportStatus status;
    private Integer weight;
    private String note;
    private Integer typeId;
}
