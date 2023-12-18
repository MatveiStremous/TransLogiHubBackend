package com.example.commonservice.repository;

import com.example.commonservice.model.Transportation;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransportationRepository extends JpaRepository<Transportation, Integer> {
    List<Transportation> findAllByOrderId(Integer orderId, Sort sort);

    List<Transportation> findAllByConvoyId(Integer convoyId, Sort sort);
}
