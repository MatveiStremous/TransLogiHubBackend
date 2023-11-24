package com.example.commonservice.controller;

import com.example.commonservice.dto.TransportationRequest;
import com.example.commonservice.dto.TransportationResponse;
import com.example.commonservice.service.TransportationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("common/transportation")
@RequiredArgsConstructor
public class TransportationController {
    private final TransportationService transportationService;

    @PostMapping
    public TransportationResponse addTransportation(@RequestBody TransportationRequest transportationRequest) {
        return transportationService.add(transportationRequest);
    }

    @GetMapping
    public List<TransportationResponse> getTransportations() {
        return transportationService.getAll();
    }

    @GetMapping("{transportationId}")
    public TransportationResponse getTransportationById(@PathVariable Integer transportationId) {
        return transportationService.getById(transportationId);
    }

    @PutMapping("{transportationId}")
    public TransportationResponse updateTransportation(@PathVariable Integer transportationId,
                                                 @RequestBody TransportationRequest transportationRequest) {
        return transportationService.updateById(transportationId, transportationRequest);
    }
}