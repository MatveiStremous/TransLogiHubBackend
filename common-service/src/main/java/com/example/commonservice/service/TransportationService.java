package com.example.commonservice.service;


import com.example.commonservice.dto.TransportationRequest;
import com.example.commonservice.dto.TransportationResponse;

import java.util.List;

public interface TransportationService {
    TransportationResponse add(TransportationRequest transportationRequest);

    List<TransportationResponse> getAll();

    TransportationResponse getById(Integer transportationId);

    TransportationResponse updateById(Integer transportationId, TransportationRequest transportationRequest);
}
