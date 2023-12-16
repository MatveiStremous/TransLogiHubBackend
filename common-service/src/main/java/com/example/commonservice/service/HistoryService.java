package com.example.commonservice.service;

import com.example.commonservice.dto.HistoryResponse;
import com.example.commonservice.model.enums.HistoryType;

import java.util.List;

public interface HistoryService {
    HistoryResponse add(Integer entityId, HistoryType type, String message);

    List<HistoryResponse> getAllByEntityIdAndType(Integer entityId, HistoryType type);
}
