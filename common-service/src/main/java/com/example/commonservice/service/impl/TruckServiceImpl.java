package com.example.commonservice.service.impl;

import com.example.commonservice.dto.TruckRequest;
import com.example.commonservice.dto.TruckResponse;
import com.example.commonservice.exception.BusinessException;
import com.example.commonservice.model.Truck;
import com.example.commonservice.model.enums.TransportStatus;
import com.example.commonservice.repository.TruckRepository;
import com.example.commonservice.service.TruckService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TruckServiceImpl implements TruckService {
    private final TruckRepository truckRepository;
    private final ModelMapper modelMapper;
    private final String STATE_NUMBER_ALREADY_IN_USE = "This state number is already in use.";
    private final String TRUCK_IS_NOT_EXIST = "Truck with this id is not exist.";
    private final String TRUCK_ALREADY_NOT_ACTIVE = "This truck is already not active.";

    @Override
    public TruckResponse add(TruckRequest truckRequest) {
        checkIfStateNumberInUse(truckRequest.getStateNumber());
        Truck truck = modelMapper.map(truckRequest, Truck.class);
        truck.setIsActive(true);
        truck.setId(null);
        truck.setStatus(TransportStatus.FREE);
        Truck newTruck = truckRepository.save(truck);
        return modelMapper.map(newTruck, TruckResponse.class);
    }

    @Override
    public List<TruckResponse> getAll() {
        return truckRepository.findAll()
                .stream()
                .map(x -> modelMapper.map(x, TruckResponse.class))
                .toList();
    }

    @Override
    public List<TruckResponse> getAllActive() {
        return truckRepository.findAllByIsActiveTrue()
                .stream()
                .map(x -> modelMapper.map(x, TruckResponse.class))
                .toList();
    }

    @Override
    public TruckResponse update(Integer id, TruckRequest truckRequest) {
        TruckResponse truckFromDb = getById(id);
        if (!truckFromDb.getStateNumber().equals(truckRequest.getStateNumber())) {
            checkIfStateNumberInUse(truckRequest.getStateNumber());
        }
        Truck truckForUpdate = modelMapper.map(truckRequest, Truck.class);
        truckForUpdate.setId(id);
        truckForUpdate.setIsActive(truckFromDb.getIsActive());
        truckForUpdate.setStatus(truckFromDb.getStatus());
        Truck updatedTruck = truckRepository.save(truckForUpdate);
        return modelMapper.map(updatedTruck, TruckResponse.class);
    }

    @Override
    public TruckResponse getById(Integer truckId) {
        Optional<Truck> optionalTruck = truckRepository.findById(truckId);
        if (optionalTruck.isEmpty()) {
            throw new BusinessException(HttpStatus.CONFLICT, TRUCK_IS_NOT_EXIST);
        }
        return modelMapper.map(optionalTruck.get(), TruckResponse.class);
    }

    @Override
    public void deleteById(Integer truckId) {
        TruckResponse truckFromDb = getById(truckId);
        if (!truckFromDb.getIsActive()) {
            throw new BusinessException(HttpStatus.CONFLICT, TRUCK_ALREADY_NOT_ACTIVE);
        } else {
            truckFromDb.setIsActive(false);
            truckRepository.save(modelMapper.map(truckFromDb, Truck.class));
        }
    }

    private void checkIfStateNumberInUse(String stateNumber) {
        if (truckRepository.findByStateNumberAndIsActiveTrue(stateNumber).isPresent()) {
            throw new BusinessException(HttpStatus.CONFLICT, STATE_NUMBER_ALREADY_IN_USE);
        }
    }
}
