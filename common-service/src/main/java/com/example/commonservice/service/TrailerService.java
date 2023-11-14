package com.example.commonservice.service;

import com.example.commonservice.dto.TrailerRequest;
import com.example.commonservice.dto.TrailerResponse;

import java.util.List;

public interface TrailerService {
    TrailerResponse add(TrailerRequest trailerRequest);

    List<TrailerResponse> getAll();

    List<TrailerResponse> getAllActive();

    TrailerResponse update(Integer id, TrailerRequest trailerRequest);

    TrailerResponse getById(Integer trailerId);

    void deleteById(Integer trailerId);
}
