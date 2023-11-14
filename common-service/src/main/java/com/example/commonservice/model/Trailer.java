package com.example.commonservice.model;

import com.example.commonservice.model.enums.TransportStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trailer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String stateNumber;
    private Integer maxCargoWeight;
    @Enumerated(EnumType.STRING)
    private TransportStatus status;
    private Integer weight;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private TrailerType trailerType;
    private String note;
    @Column(columnDefinition = "boolean default true")
    private Boolean isActive;
}
