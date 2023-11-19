package com.example.commonservice.service;

import com.example.commonservice.dto.ConvoyRequest;
import com.example.commonservice.dto.ConvoyResponse;

import java.util.List;

public interface ConvoyService {
    ConvoyResponse add(ConvoyRequest convoyRequest);

    List<ConvoyResponse> getAll();

    List<ConvoyResponse> getAllActive();

    ConvoyResponse getById(Integer convoyId);

    ConvoyResponse updateById(Integer convoyId, ConvoyRequest convoyRequest);

    void deleteById(Integer convoyId);
}
