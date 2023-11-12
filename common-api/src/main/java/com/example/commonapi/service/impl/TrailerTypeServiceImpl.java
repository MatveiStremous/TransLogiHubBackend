package com.example.commonapi.service.impl;

import com.example.commonapi.dto.TrailerTypeRequest;
import com.example.commonapi.dto.TrailerTypeResponse;
import com.example.commonapi.exception.BusinessException;
import com.example.commonapi.model.TrailerType;
import com.example.commonapi.repository.TrailerTypeRepository;
import com.example.commonapi.service.TrailerTypeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrailerTypeServiceImpl implements TrailerTypeService {
    private final TrailerTypeRepository trailerTypeRepository;
    private final ModelMapper modelMapper;
    private final String TRAILER_TYPE_NAME_ALREADY_EXISTS = "Trailer type name already exists.";
    private final String TRAILER_TYPE_NAME_ALREADY_EXISTS_BUT_NOT_ACTIVE = "Trailer type name already exists but not active now.";
    private final String TRAILER_TYPE_IS_NOT_EXIST = "Trailer type with this id is not exist.";
    private final String TRAILER_TYPE_ALREADY_NOT_ACTIVE = "This trailer type is already not active.";

    @Override
    public TrailerTypeResponse add(TrailerTypeRequest trailerTypeRequest) {
        TrailerType newTrailerType = modelMapper.map(trailerTypeRequest, TrailerType.class);
        if (trailerTypeRepository.findByNameAndIsActiveFalse(trailerTypeRequest.getName()).isEmpty()) {
            checkIfNameInUse(trailerTypeRequest.getName());
        } else {
            newTrailerType.setId(trailerTypeRepository.findByName(trailerTypeRequest.getName()).get().getId());
        }
        newTrailerType.setIsActive(true);
        newTrailerType = trailerTypeRepository.save(newTrailerType);
        return modelMapper.map(newTrailerType, TrailerTypeResponse.class);
    }

    @Override
    public List<TrailerTypeResponse> getAll() {
        return trailerTypeRepository.findAll()
                .stream()
                .map(trailerType -> modelMapper.map(trailerType, TrailerTypeResponse.class))
                .toList();
    }

    @Override
    public List<TrailerTypeResponse> getAllActive() {
        return trailerTypeRepository.findAllByIsActiveTrue()
                .stream()
                .map(trailerType -> modelMapper.map(trailerType, TrailerTypeResponse.class))
                .toList();
    }

    @Override
    public TrailerTypeResponse getById(Integer trailerTypeId) {
        Optional<TrailerType> optionalTrailerType = trailerTypeRepository.findById(trailerTypeId);
        if (optionalTrailerType.isEmpty()) {
            throw new BusinessException(HttpStatus.CONFLICT, TRAILER_TYPE_IS_NOT_EXIST);
        }
        return modelMapper.map(optionalTrailerType.get(), TrailerTypeResponse.class);
    }

    @Override
    public TrailerTypeResponse updateById(Integer trailerTypeId, TrailerTypeRequest trailerTypeRequest) {
        TrailerTypeResponse trailerTypeFromDb = getById(trailerTypeId);
        if (!trailerTypeFromDb.getName().equals(trailerTypeRequest.getName())) {
            if (trailerTypeRepository.findByNameAndIsActiveFalse(trailerTypeRequest.getName()).isEmpty()) {
                checkIfNameInUse(trailerTypeRequest.getName());
            } else {
                throw new BusinessException(HttpStatus.CONFLICT, TRAILER_TYPE_NAME_ALREADY_EXISTS_BUT_NOT_ACTIVE);
            }
        } else {
            return trailerTypeFromDb;
        }
        TrailerType trailerTypeForUpdate = modelMapper.map(trailerTypeRequest, TrailerType.class);
        trailerTypeForUpdate.setId(trailerTypeId);
        trailerTypeForUpdate.setIsActive(trailerTypeFromDb.getIsActive());
        TrailerType updatedTrailerType = trailerTypeRepository.save(trailerTypeForUpdate);
        return modelMapper.map(updatedTrailerType, TrailerTypeResponse.class);
    }

    @Override
    public void deleteById(Integer trailerTypeId) {
        TrailerTypeResponse trailerTypeFromDb = getById(trailerTypeId);
        if (!trailerTypeFromDb.getIsActive()) {
            throw new BusinessException(HttpStatus.CONFLICT, TRAILER_TYPE_ALREADY_NOT_ACTIVE);
        } else {
            trailerTypeFromDb.setIsActive(false);
            trailerTypeRepository.save(modelMapper.map(trailerTypeFromDb, TrailerType.class));
        }
    }

    private void checkIfNameInUse(String name) {
        if (trailerTypeRepository.findByName(name).isPresent()) {
            throw new BusinessException(HttpStatus.CONFLICT, TRAILER_TYPE_NAME_ALREADY_EXISTS);
        }
    }
}
