package com.example.commonapi.service;

import com.example.commonapi.dto.TrailerTypeRequest;
import com.example.commonapi.dto.TrailerTypeResponse;

import java.util.List;

public interface TrailerTypeService {
    TrailerTypeResponse add(TrailerTypeRequest trailerTypeRequest);

    List<TrailerTypeResponse> getAll();

    List<TrailerTypeResponse> getAllActive();

    TrailerTypeResponse getById(Integer trailerTypeId);

    TrailerTypeResponse updateById(Integer trailerTypeId, TrailerTypeRequest trailerTypeRequest);

    void deleteById(Integer trailerTypeId);
}
