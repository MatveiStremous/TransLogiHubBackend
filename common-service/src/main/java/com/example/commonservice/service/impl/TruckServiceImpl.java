package com.example.commonservice.service.impl;

import com.example.commonservice.dto.TruckInfoResponse;
import com.example.commonservice.dto.TruckRequest;
import com.example.commonservice.dto.TruckResponse;
import com.example.commonservice.exception.BusinessException;
import com.example.commonservice.model.Truck;
import com.example.commonservice.model.enums.Role;
import com.example.commonservice.model.enums.TransportStatus;
import com.example.commonservice.repository.TruckRepository;
import com.example.commonservice.security.JWTUtil;
import com.example.commonservice.service.TruckService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TruckServiceImpl implements TruckService {
    private final TruckRepository truckRepository;
    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;

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
        return truckRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream()
                .map(x -> modelMapper.map(x, TruckResponse.class))
                .toList();
    }

    @Override
    public List<TruckResponse> getAllActive() {
        String role = jwtUtil.getClaimFromToken("role");
        if (role.equals(Role.ROLE_MANAGER.toString())) {
            return truckRepository.findAllByIsActiveTrue(Sort.by(Sort.Direction.ASC, "id"))
                    .stream()
                    .map(x -> modelMapper.map(x, TruckResponse.class))
                    .toList();
        } else {
            Integer convoyId = jwtUtil.getIntClaimFromToken("convoyId");
            return truckRepository.findAllByIsActiveTrueAndConvoyId(convoyId, Sort.by(Sort.Direction.ASC, "id"))
                    .stream()
                    .map(x -> modelMapper.map(x, TruckResponse.class))
                    .toList();
        }
    }

    @Override
    public TruckResponse update(Integer id, TruckRequest truckRequest) {
        Truck truckFromDb = getEntityById(id);
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
    public TruckInfoResponse getById(Integer truckId) {
        return modelMapper.map(getEntityById(truckId), TruckInfoResponse.class);
    }

    @Override
    public void deleteById(Integer truckId) {
        Truck truckFromDb = getEntityById(truckId);
        if (!truckFromDb.getIsActive()) {
            throw new BusinessException(HttpStatus.CONFLICT, "TRUCK-3");
        } else {
            truckFromDb.setIsActive(false);
            truckRepository.save(modelMapper.map(truckFromDb, Truck.class));
        }
    }

    private void checkIfStateNumberInUse(String stateNumber) {
        if (truckRepository.findByStateNumberAndIsActiveTrue(stateNumber).isPresent()) {
            throw new BusinessException(HttpStatus.CONFLICT, "TRUCK-1");
        }
    }

    private Truck getEntityById(Integer truckId) {
        return truckRepository.findById(truckId)
                .orElseThrow(() -> new BusinessException(HttpStatus.CONFLICT, "TRUCK-2"));
    }
}
