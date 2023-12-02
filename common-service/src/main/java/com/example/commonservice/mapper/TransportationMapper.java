package com.example.commonservice.mapper;

import com.example.commonservice.dto.TransportationRequest;
import com.example.commonservice.model.Convoy;
import com.example.commonservice.model.Order;
import com.example.commonservice.model.Trailer;
import com.example.commonservice.model.Transportation;
import com.example.commonservice.model.Truck;
import com.example.commonservice.repository.OrderRepository;
import com.example.commonservice.service.ConvoyService;
import com.example.commonservice.service.TrailerService;
import com.example.commonservice.service.TruckService;
import com.example.commonservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransportationMapper {
    private final ModelMapper mapper;
    private final ConvoyService convoyService;
    private final UserService userService;
    private final TruckService truckService;
    private final TrailerService trailerService;
    private final OrderRepository orderRepository;

    public Transportation map(TransportationRequest transportationRequest) {
        Transportation transportation = new Transportation();
        transportation.setId(null);
        transportation.setDateOfLoading(transportationRequest.getDateOfLoading());
        transportation.setDateOfUnloading(transportationRequest.getDateOfUnloading());
        transportation.setFinalDistance(transportationRequest.getFinalDistance());
        transportation.setWeight(transportationRequest.getWeight());
        transportation.setSpentFuel(transportationRequest.getSpentFuel());
        transportation.setNote(transportationRequest.getNote());
        if (transportationRequest.getConvoyId() != null) {
            Convoy convoy = mapper.map(convoyService.getById(transportationRequest.getConvoyId()), Convoy.class);
            convoy.setId(transportationRequest.getConvoyId());
            transportation.setConvoy(convoy);
        }
        if (transportationRequest.getDriverId() != null) {
            transportation.setDriver(userService.getEntityById(transportationRequest.getDriverId()));
        }
        if (transportationRequest.getTruckId() != null) {
            Truck truck = mapper.map(truckService.getById(transportationRequest.getTruckId()), Truck.class);
            truck.setId(transportationRequest.getTruckId());
            transportation.setTruck(truck);
        }
        if (transportationRequest.getTruckId() != null) {
            Trailer trailer = mapper.map(trailerService.getById(transportationRequest.getTrailerId()), Trailer.class);
            trailer.setId(transportationRequest.getTrailerId());
            transportation.setTrailer(trailer);
        }
        if (transportationRequest.getOrderId() != null) {
            Order order = orderRepository.findById(transportationRequest.getOrderId()).orElse(null);
            transportation.setOrder(order);
        }
        return transportation;
    }
}
