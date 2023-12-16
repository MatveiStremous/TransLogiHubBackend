package com.example.commonservice.mapper;

import com.example.commonservice.dto.ConvoyResponse;
import com.example.commonservice.dto.OrderResponse;
import com.example.commonservice.dto.TrailerResponse;
import com.example.commonservice.dto.TransportationInfoResponse;
import com.example.commonservice.dto.TransportationRequest;
import com.example.commonservice.dto.TransportationResponse;
import com.example.commonservice.dto.TruckResponse;
import com.example.commonservice.dto.UserResponse;
import com.example.commonservice.model.Transportation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransportationMapper {
    private final ModelMapper mapper;

    public Transportation mapToEntity(TransportationRequest transportationRequest) {
        Transportation transportation = new Transportation();
        transportation.setId(null);
        transportation.setDateOfLoading(transportationRequest.getDateOfLoading());
        transportation.setDateOfUnloading(transportationRequest.getDateOfUnloading());
        transportation.setFinalDistance(transportationRequest.getFinalDistance());
        transportation.setWeight(transportationRequest.getWeight());
        transportation.setSpentFuel(transportationRequest.getSpentFuel());
        transportation.setNote(transportationRequest.getNote());
        transportation.setOrderId(transportationRequest.getOrderId());
        return transportation;
    }

    public TransportationResponse mapToResponse(Transportation transportation) {
        TransportationResponse transportationResponse= new TransportationResponse();
        transportationResponse.setId(transportation.getId());
        transportationResponse.setDateOfLoading(transportation.getDateOfLoading());
        transportationResponse.setDateOfUnloading(transportation.getDateOfUnloading());
        transportationResponse.setFinalDistance(transportation.getFinalDistance());
        transportationResponse.setWeight(transportation.getWeight());
        transportationResponse.setSpentFuel(transportation.getSpentFuel());
        transportationResponse.setNote(transportation.getNote());
        transportationResponse.setOrder(transportation.getOrder() == null ? null : mapper.map(transportation.getOrder(), OrderResponse.class));
        transportationResponse.setConvoy(transportation.getConvoy() == null ? null : mapper.map(transportation.getConvoy(), ConvoyResponse.class));
        transportationResponse.setTrailer(transportation.getTrailer() == null ? null : mapper.map(transportation.getTrailer(), TrailerResponse.class));
        transportationResponse.setTruck(transportation.getTruck() == null ? null : mapper.map(transportation.getTruck(), TruckResponse.class));
        transportationResponse.setDriver(transportation.getDriver() == null ? null : mapper.map(transportation.getDriver(), UserResponse.class));
        return transportationResponse;
    }

    public TransportationInfoResponse mapToInfoResponse(Transportation transportation) {
        TransportationInfoResponse transportationResponse= new TransportationInfoResponse();
        transportationResponse.setId(transportation.getId());
        transportationResponse.setDateOfLoading(transportation.getDateOfLoading());
        transportationResponse.setDateOfUnloading(transportation.getDateOfUnloading());
        transportationResponse.setFinalDistance(transportation.getFinalDistance());
        transportationResponse.setWeight(transportation.getWeight());
        transportationResponse.setSpentFuel(transportation.getSpentFuel());
        transportationResponse.setNote(transportation.getNote());
        transportationResponse.setOrder(transportation.getOrder() == null ? null : mapper.map(transportation.getOrder(), OrderResponse.class));
        transportationResponse.setConvoy(transportation.getConvoy() == null ? null : mapper.map(transportation.getConvoy(), ConvoyResponse.class));
        transportationResponse.setTrailer(transportation.getTrailer() == null ? null : mapper.map(transportation.getTrailer(), TrailerResponse.class));
        transportationResponse.setTruck(transportation.getTruck() == null ? null : mapper.map(transportation.getTruck(), TruckResponse.class));
        transportationResponse.setDriver(transportation.getDriver() == null ? null : mapper.map(transportation.getDriver(), UserResponse.class));
        return transportationResponse;
    }
}
