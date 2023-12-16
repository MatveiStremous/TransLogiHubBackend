package com.example.commonservice.controller;

import com.example.commonservice.dto.TransportationInfoResponse;
import com.example.commonservice.dto.TransportationRequest;
import com.example.commonservice.dto.TransportationResponse;
import com.example.commonservice.model.enums.TransportationStatus;
import com.example.commonservice.service.TransportationService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
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
    public TransportationInfoResponse getTransportationById(@PathVariable Integer transportationId) {
        return transportationService.getById(transportationId);
    }

    @GetMapping("/order/{orderId}")
    public List<TransportationResponse> getAllTransportationsByOrderId(@PathVariable Integer orderId) {
        return transportationService.getAllByOrderId(orderId);
    }

    @PutMapping("{transportationId}")
    public TransportationResponse updateTransportation(@PathVariable Integer transportationId,
                                                 @RequestBody TransportationRequest transportationRequest) {
        return transportationService.updateById(transportationId, transportationRequest);
    }

    @GetMapping("pdf/{transportationId}")
    public ByteArrayResource getPdfForTransportation(@PathVariable Integer transportationId){
        return transportationService.generatePdfByTransportationId(transportationId);
    }

    @PutMapping("/{transportationId}/driver/{driverId}")
    public TransportationResponse setDriver(@PathVariable Integer transportationId,
                                            @PathVariable Integer driverId) {
        return transportationService.setDriver(transportationId, driverId);
    }

    @PutMapping("/{transportationId}/truck/{truckId}")
    public TransportationResponse setTruck(@PathVariable Integer transportationId,
                                            @PathVariable Integer truckId) {
        return transportationService.setTruck(transportationId, truckId);
    }

    @PutMapping("/{transportationId}/trailer/{trailerId}")
    public TransportationResponse setTrailer(@PathVariable Integer transportationId,
                                            @PathVariable Integer trailerId) {
        return transportationService.setTrailer(transportationId, trailerId);
    }

    @PutMapping("/{transportationId}/convoy/{convoyId}")
    public TransportationResponse setConvoy(@PathVariable Integer transportationId,
                                            @PathVariable Integer convoyId) {
        return transportationService.setConvoy(transportationId, convoyId);
    }

    @PutMapping("/{transportationId}/status/{status}")
    public TransportationResponse changeStatus(@PathVariable Integer transportationId,
                                            @PathVariable TransportationStatus status) {
        return transportationService.changeStatus(transportationId, status);
    }
}
