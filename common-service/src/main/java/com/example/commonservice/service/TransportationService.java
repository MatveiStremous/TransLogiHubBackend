package com.example.commonservice.service;


import com.example.commonservice.dto.TransportationInfoResponse;
import com.example.commonservice.dto.TransportationRequest;
import com.example.commonservice.dto.TransportationResponse;
import org.springframework.core.io.ByteArrayResource;

import java.util.List;

public interface TransportationService {
    TransportationResponse add(TransportationRequest transportationRequest);

    List<TransportationResponse> getAll();

    List<TransportationResponse> getAllByOrderId(Integer orderId);

    TransportationInfoResponse getById(Integer transportationId);

    TransportationResponse updateById(Integer transportationId, TransportationRequest transportationRequest);

    ByteArrayResource generatePdfByTransportationId(Integer transportationId);
}
