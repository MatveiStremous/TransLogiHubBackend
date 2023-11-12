package com.example.commonapi.service;

import com.example.commonapi.dto.TrailerRequest;
import com.example.commonapi.dto.TrailerResponse;

import java.util.List;

public interface TrailerService {
    TrailerResponse add(TrailerRequest trailerRequest);

    List<TrailerResponse> getAll();

    List<TrailerResponse> getAllActive();

    TrailerResponse update(Integer id, TrailerRequest trailerRequest);

    TrailerResponse getById(Integer trailerId);

    void deleteById(Integer trailerId);
}
