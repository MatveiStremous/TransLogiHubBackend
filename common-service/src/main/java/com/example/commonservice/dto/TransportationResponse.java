package com.example.commonservice.dto;

import com.example.commonservice.model.enums.TransportationStatus;
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
public class TransportationResponse {
    private Integer id;
    private Integer weight;
    private Integer finalDistance;
    private Integer spentFuel;
    private String note;
    private LocalDateTime dateOfLoading;
    private LocalDateTime dateOfUnloading;
    private TransportationStatus status;
    private OrderResponse order;
    private UserResponse driver;
    private TruckResponse truck;
    private TrailerResponse trailer;
    private ConvoyResponse convoy;
}
