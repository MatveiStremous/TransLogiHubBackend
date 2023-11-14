package com.example.commonservice.repository;

import com.example.commonservice.model.Truck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TruckRepository extends JpaRepository<Truck, Integer> {
    Optional<Truck> findByStateNumberAndIsActiveTrue(String stateNumber);

    List<Truck> findAllByIsActiveTrue();
}
