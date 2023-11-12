package com.example.commonapi.repository;

import com.example.commonapi.model.TrailerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrailerTypeRepository extends JpaRepository<TrailerType, Integer> {
    Optional<TrailerType> findByName(String name);

    Optional<TrailerType> findByNameAndIsActiveFalse(String name);

    List<TrailerType> findAllByIsActiveTrue();
}
