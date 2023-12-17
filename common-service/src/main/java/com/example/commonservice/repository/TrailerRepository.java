package com.example.commonservice.repository;

import com.example.commonservice.model.Trailer;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrailerRepository extends JpaRepository<Trailer, Integer> {
    Optional<Trailer> findByStateNumberAndIsActiveTrue(String stateNumber);

    List<Trailer> findAllByIsActiveTrue(Sort id);
    List<Trailer> findAllByIsActiveTrueAndConvoyId(Integer ConvoyId, Sort id);
}
