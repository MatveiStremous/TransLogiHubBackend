package com.example.commonservice.service.impl;

import com.example.commonservice.dto.TransportationRequest;
import com.example.commonservice.dto.TransportationResponse;
import com.example.commonservice.exception.BusinessException;
import com.example.commonservice.mapper.TransportationMapper;
import com.example.commonservice.model.Transportation;
import com.example.commonservice.model.enums.TransportationStatus;
import com.example.commonservice.repository.TransportationRepository;
import com.example.commonservice.service.TransportationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransportationServiceImpl implements TransportationService {
    private final TransportationRepository transportationRepository;
    private final ModelMapper modelMapper;
    private final TransportationMapper transportationMapper;
    private final String TRANSPORTATION_IS_NOT_EXIST = "Transportation with this id is not exist.";

    @Override
    public TransportationResponse add(TransportationRequest transportationRequest) {
        Transportation newTransportation = transportationMapper.map(transportationRequest);
        newTransportation.setStatus(TransportationStatus.FORMED);
        newTransportation = transportationRepository.save(newTransportation);
        return modelMapper.map(newTransportation, TransportationResponse.class);
    }

    @Override
    public List<TransportationResponse> getAll() {
        return transportationRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))
                .stream()
                .map(transportation -> modelMapper.map(transportation, TransportationResponse.class))
                .toList();
    }

    @Override
    public TransportationResponse getById(Integer transportationId) {
        Optional<Transportation> optionalTransportation = transportationRepository.findById(transportationId);
        if (optionalTransportation.isEmpty()) {
            throw new BusinessException(HttpStatus.CONFLICT, TRANSPORTATION_IS_NOT_EXIST);
        }
        return modelMapper.map(optionalTransportation.get(), TransportationResponse.class);
    }

    @Override
    public TransportationResponse updateById(Integer transportationId, TransportationRequest transportationRequest) {
        TransportationResponse transportationFromDb = getById(transportationId);
        Transportation transportationForUpdate = transportationMapper.map(transportationRequest);
        transportationForUpdate.setId(transportationId);
        transportationForUpdate.setStatus(transportationFromDb.getStatus());
        Transportation updatedTransportation = transportationRepository.save(transportationForUpdate);
        return modelMapper.map(updatedTransportation, TransportationResponse.class);
    }
}
