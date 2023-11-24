package com.example.commonservice.repository;

import com.example.commonservice.model.Convoy;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConvoyRepository extends JpaRepository<Convoy, Integer> {
    Optional<Convoy> findByName(String name);

    Optional<Convoy> findByNameAndIsActiveFalse(String name);

    List<Convoy> findAllByIsActiveTrue(Sort id);
}
