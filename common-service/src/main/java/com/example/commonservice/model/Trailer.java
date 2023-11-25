package com.example.commonservice.model;

import com.example.commonservice.model.enums.TransportStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Year;
import java.util.List;

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
    private String brand;
    private String model;
    private String stateNumber;
    private Year yearOfIssue;
    private Integer maxCargoWeight;
    @Enumerated(EnumType.STRING)
    private TransportStatus status;
    private Integer weight;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private TrailerType trailerType;
    private String note;
    @Column(columnDefinition = "boolean default true")
    private Boolean isActive;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "trailer")
    private List<Transportation> transportations;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private Convoy convoy;
}
