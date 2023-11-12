package com.example.commonapi.model;

import com.example.commonapi.model.enums.TransportStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Year;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Truck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String stateNumber;
    private Integer mileage;
    private Year yearOfIssue;
    private Integer maxCargoWeight;
    @Enumerated(EnumType.STRING)
    private TransportStatus status;
    private Integer weight;
    private String note;
    @Column(columnDefinition = "boolean default true")
    private Boolean isActive;
}
