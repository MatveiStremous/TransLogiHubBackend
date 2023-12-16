package com.example.commonservice.service.impl;

import com.example.commonservice.dto.TransportationInfoResponse;
import com.example.commonservice.dto.TransportationRequest;
import com.example.commonservice.dto.TransportationResponse;
import com.example.commonservice.exception.BusinessException;
import com.example.commonservice.mapper.TransportationMapper;
import com.example.commonservice.model.Order;
import com.example.commonservice.model.Transportation;
import com.example.commonservice.model.enums.TransportationStatus;
import com.example.commonservice.repository.TransportationRepository;
import com.example.commonservice.service.TransportationService;
import com.example.commonservice.util.PdfGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransportationServiceImpl implements TransportationService {
    private final TransportationRepository transportationRepository;
    private final TransportationMapper transportationMapper;

    @Override
    public TransportationResponse add(TransportationRequest transportationRequest) {
        Transportation newTransportation = transportationMapper.mapToEntity(transportationRequest);
        newTransportation.setStatus(TransportationStatus.FORMED);
        newTransportation = transportationRepository.save(newTransportation);
        return transportationMapper.mapToResponse(newTransportation);
    }

    @Override
    public List<TransportationResponse> getAll() {
        return transportationRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))
                .stream()
                .map(transportationMapper::mapToResponse)
                .toList();
    }

    @Override
    public List<TransportationResponse> getAllByOrderId(Integer orderId) {
        return transportationRepository.findAllByOrderId(orderId, Sort.by(Sort.Direction.DESC, "id"))
                .stream()
                .map(transportationMapper::mapToResponse)
                .toList();
    }

    @Override
    public TransportationInfoResponse getById(Integer transportationId) {
        return transportationMapper.mapToInfoResponse(getEntityById(transportationId));
    }

    @Override
    public TransportationResponse updateById(Integer transportationId, TransportationRequest transportationRequest) {
        Transportation transportationFromDb = getEntityById(transportationId);
        Transportation transportationForUpdate = transportationMapper.mapToEntity(transportationRequest);
        transportationForUpdate.setId(transportationId);
        transportationForUpdate.setStatus(transportationFromDb.getStatus());
        Transportation updatedTransportation = transportationRepository.save(transportationForUpdate);
        return transportationMapper.mapToResponse(updatedTransportation);
    }

    @Override
    public ByteArrayResource generatePdfByTransportationId(Integer transportationId) {
        return PdfGenerator.generatePdfByTransportation(getById(transportationId));
    }

    private Transportation getEntityById(Integer id){
        return transportationRepository.findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.CONFLICT, "TRANSPORTATION-1"));
    }
}
