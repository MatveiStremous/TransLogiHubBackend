package com.example.commonservice.service;


import com.example.commonservice.dto.TransportationInfoResponse;
import com.example.commonservice.dto.TransportationRequest;
import com.example.commonservice.dto.TransportationResponse;
import com.example.commonservice.model.enums.TransportationStatus;
import org.springframework.core.io.ByteArrayResource;

import java.util.List;

public interface TransportationService {
    TransportationResponse add(TransportationRequest transportationRequest);

    List<TransportationResponse> getAll();

    List<TransportationResponse> getAllByOrderId(Integer orderId);

    TransportationInfoResponse getById(Integer transportationId);

    TransportationResponse updateById(Integer transportationId, TransportationRequest transportationRequest);

    ByteArrayResource generatePdfByTransportationId(Integer transportationId);

    TransportationResponse setDriver(Integer transportationId, Integer driverId);

    TransportationResponse setTrailer(Integer transportationId, Integer trailerId);

    TransportationResponse setTruck(Integer transportationId, Integer truckId);

    TransportationResponse setConvoy(Integer transportationId, Integer convoyId);

    TransportationResponse changeStatus(Integer transportationId, TransportationStatus status);

    TransportationResponse deleteConvoy(Integer transportationId);

    TransportationResponse deleteDriver(Integer transportationId);

    TransportationResponse deleteTruck(Integer transportationId);

    TransportationResponse deleteTrailer(Integer transportationId);
}
