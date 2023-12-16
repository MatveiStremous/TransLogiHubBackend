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
import jakarta.persistence.JoinColumn;
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
public class Truck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String brand;
    private String model;
    private String stateNumber;
    private Integer mileage;
    private Year yearOfIssue;
    private Integer maxCargoWeight;
    @Enumerated(EnumType.STRING)
    private TransportStatus status;
    private Integer weight;
    private String note;
    @Column(name = "convoy_id")
    private Integer convoyId;
    @Column(columnDefinition = "boolean default true")
    private Boolean isActive;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "truck")
    private List<Transportation> transportations;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "convoy_id", updatable = false, insertable = false)
    private Convoy convoy;
}
