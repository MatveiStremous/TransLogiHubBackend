package com.example.commonservice.service;

import com.example.commonservice.dto.TrailerTypeRequest;
import com.example.commonservice.dto.TrailerTypeResponse;

import java.util.List;

public interface TrailerTypeService {
    TrailerTypeResponse add(TrailerTypeRequest trailerTypeRequest);

    List<TrailerTypeResponse> getAll();

    List<TrailerTypeResponse> getAllActive();

    TrailerTypeResponse getById(Integer trailerTypeId);

    TrailerTypeResponse updateById(Integer trailerTypeId, TrailerTypeRequest trailerTypeRequest);

    void deleteById(Integer trailerTypeId);
}
