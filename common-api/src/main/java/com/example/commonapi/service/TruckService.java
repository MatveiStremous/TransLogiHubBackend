package com.example.commonapi.service;

import com.example.commonapi.dto.TruckRequest;
import com.example.commonapi.dto.TruckResponse;

import java.util.List;

public interface TruckService {
    TruckResponse add(TruckRequest truckRequest);

    List<TruckResponse> getAll();

    List<TruckResponse> getAllActive();

    TruckResponse update(Integer id, TruckRequest truckRequest);

    TruckResponse getById(Integer truckId);

    void deleteById(Integer truckId);
}
