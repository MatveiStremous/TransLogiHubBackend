package com.example.commonservice.repository;

import com.example.commonservice.model.History;
import com.example.commonservice.model.enums.HistoryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Integer> {
    List<History> findAllByEntityIdAndType(Integer entityId, HistoryType type);
}
