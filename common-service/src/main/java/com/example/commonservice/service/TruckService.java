package com.example.commonservice.service;

import com.example.commonservice.dto.TruckRequest;
import com.example.commonservice.dto.TruckResponse;

import java.util.List;

public interface TruckService {
    TruckResponse add(TruckRequest truckRequest);

    List<TruckResponse> getAll();

    List<TruckResponse> getAllActive();

    TruckResponse update(Integer id, TruckRequest truckRequest);

    TruckResponse getById(Integer truckId);

    void deleteById(Integer truckId);
}
