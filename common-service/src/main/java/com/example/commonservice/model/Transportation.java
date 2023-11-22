package com.example.commonservice.model;

import com.example.commonservice.model.enums.TransportationStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transportation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer weight;
    private Integer finalDistance;
    private Integer spentFuel;
    private String note;
    private LocalDateTime dateOfLoading;
    private LocalDateTime dateOfUnloading;
    @Enumerated(EnumType.STRING)
    private TransportationStatus status;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private Order order;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private User driver;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private Truck truck;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private Trailer trailer;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private Convoy convoy;
}