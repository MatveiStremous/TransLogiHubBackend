package com.example.commonservice.dto;

import com.example.commonservice.model.enums.TransportStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrailerResponse {
    private Integer id;
    private String stateNumber;
    private Integer maxCargoWeight;
    private TransportStatus status;
    private Integer weight;
    private String note;
    private Boolean isActive;
    private Integer typeId;
}
