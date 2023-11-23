package com.example.commonservice.mapper;

import com.example.commonservice.dto.TransportationRequest;
import com.example.commonservice.model.Convoy;
import com.example.commonservice.model.Order;
import com.example.commonservice.model.Trailer;
import com.example.commonservice.model.Transportation;
import com.example.commonservice.model.Truck;
import com.example.commonservice.service.ConvoyService;
import com.example.commonservice.service.OrderService;
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
    private final OrderService orderService;

    public Transportation map(TransportationRequest transportationRequest) {
        Transportation transportation = new Transportation();
        transportation.setId(null);
        transportation.setDateOfLoading(transportationRequest.getDateOfLoading());
        transportation.setDateOfUnloading(transportationRequest.getDateOfUnloading());
        transportation.setFinalDistance(transportationRequest.getFinalDistance());
        transportation.setWeight(transportationRequest.getWeight());
        transportation.setStatus(transportationRequest.getStatus());
        transportation.setSpentFuel(transportationRequest.getSpentFuel());
        transportation.setNote(transportationRequest.getNote());
        transportation.setConvoy(mapper.map(convoyService.getById(transportationRequest.getConvoyId()), Convoy.class));
        transportation.setDriver(userService.getByLoginWithId(transportationRequest.getDriverLogin()));
        transportation.setTruck(mapper.map(truckService.getById(transportationRequest.getTruckId()), Truck.class));
        transportation.setTrailer(mapper.map(trailerService.getById(transportationRequest.getTrailerId()), Trailer.class));
        transportation.setOrder(mapper.map(orderService.getById(transportationRequest.getOrderId()), Order.class));
        return transportation;
    }
}
