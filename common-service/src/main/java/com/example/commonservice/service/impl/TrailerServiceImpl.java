package com.example.commonservice.service.impl;

import com.example.commonservice.dto.TrailerRequest;
import com.example.commonservice.dto.TrailerResponse;
import com.example.commonservice.exception.BusinessException;
import com.example.commonservice.model.Trailer;
import com.example.commonservice.model.TrailerType;
import com.example.commonservice.repository.TrailerRepository;
import com.example.commonservice.service.TrailerService;
import com.example.commonservice.service.TrailerTypeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrailerServiceImpl implements TrailerService {
    private final TrailerRepository trailerRepository;
    private final TrailerTypeService trailerTypeService;
    private final ModelMapper modelMapper;
    private final String STATE_NUMBER_ALREADY_IN_USE = "This state number is already in use.";
    private final String TRAILER_IS_NOT_EXIST = "Trailer with this id is not exist.";
    private final String TRAILER_ALREADY_NOT_ACTIVE = "This trailer is already not active.";

    @Override
    public TrailerResponse add(TrailerRequest trailerRequest) {
        checkIfStateNumberInUse(trailerRequest.getStateNumber());
        Trailer trailer = modelMapper.map(trailerRequest, Trailer.class);
        trailer.setIsActive(true);
        trailer.setTrailerType(modelMapper.map(trailerTypeService.getById(trailerRequest.getTypeId()), TrailerType.class));
        trailer.setId(null);
        Trailer newTrailer = trailerRepository.save(trailer);
        return modelMapper.map(newTrailer, TrailerResponse.class);
    }

    @Override
    public List<TrailerResponse> getAll() {
        return trailerRepository.findAll()
                .stream()
                .map(trailer -> modelMapper.map(trailer, TrailerResponse.class))
                .toList();
    }

    @Override
    public List<TrailerResponse> getAllActive() {
        return trailerRepository.findAllByIsActiveTrue()
                .stream()
                .map(trailer -> modelMapper.map(trailer, TrailerResponse.class))
                .toList();
    }

    @Override
    public TrailerResponse update(Integer id, TrailerRequest trailerRequest) {
        TrailerResponse trailerFromDb = getById(id);
        if (!trailerFromDb.getStateNumber().equals(trailerRequest.getStateNumber())) {
            checkIfStateNumberInUse(trailerRequest.getStateNumber());
        }
        Trailer trailerForUpdate = modelMapper.map(trailerRequest, Trailer.class);
        trailerForUpdate.setId(id);
        trailerForUpdate.setTrailerType(modelMapper.map(trailerTypeService.getById(trailerRequest.getTypeId()), TrailerType.class));
        trailerForUpdate.setIsActive(trailerFromDb.getIsActive());
        Trailer updatedTrailer = trailerRepository.save(trailerForUpdate);
        return modelMapper.map(updatedTrailer, TrailerResponse.class);
    }

    @Override
    public TrailerResponse getById(Integer trailerId) {
        Optional<Trailer> optionalTrailer = trailerRepository.findById(trailerId);
        if (optionalTrailer.isEmpty()) {
            throw new BusinessException(HttpStatus.CONFLICT, TRAILER_IS_NOT_EXIST);
        }
        return modelMapper.map(optionalTrailer.get(), TrailerResponse.class);
    }

    @Override
    public void deleteById(Integer trailerId) {
        TrailerResponse trailerFromDb = getById(trailerId);
        if (!trailerFromDb.getIsActive()) {
            throw new BusinessException(HttpStatus.CONFLICT, TRAILER_ALREADY_NOT_ACTIVE);
        } else {
            trailerFromDb.setIsActive(false);
            trailerRepository.save(modelMapper.map(trailerFromDb, Trailer.class));
        }
    }

    private void checkIfStateNumberInUse(String stateNumber) {
        if (trailerRepository.findByStateNumberAndIsActiveTrue(stateNumber).isPresent()) {
            throw new BusinessException(HttpStatus.CONFLICT, STATE_NUMBER_ALREADY_IN_USE);
        }
    }
}
