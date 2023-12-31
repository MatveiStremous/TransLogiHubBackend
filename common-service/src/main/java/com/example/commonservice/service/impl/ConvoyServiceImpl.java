package com.example.commonservice.service.impl;

import com.example.commonservice.dto.ConvoyRequest;
import com.example.commonservice.dto.ConvoyResponse;
import com.example.commonservice.exception.BusinessException;
import com.example.commonservice.model.Convoy;
import com.example.commonservice.repository.ConvoyRepository;
import com.example.commonservice.service.ConvoyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConvoyServiceImpl implements ConvoyService {
    private final ConvoyRepository convoyRepository;
    private final ModelMapper modelMapper;

    @Override
    public ConvoyResponse add(ConvoyRequest convoyRequest) {
        Convoy newConvoy = modelMapper.map(convoyRequest, Convoy.class);
        if (convoyRepository.findByNameAndIsActiveFalse(convoyRequest.getName()).isEmpty()) {
            checkIfNameInUse(convoyRequest.getName());
        } else {
            newConvoy.setId(convoyRepository.findByName(convoyRequest.getName()).get().getId());
        }
        newConvoy.setIsActive(true);
        newConvoy = convoyRepository.save(newConvoy);
        return modelMapper.map(newConvoy, ConvoyResponse.class);
    }

    @Override
    public List<ConvoyResponse> getAll() {
        return convoyRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream()
                .map(convoy -> modelMapper.map(convoy, ConvoyResponse.class))
                .toList();
    }

    @Override
    public List<ConvoyResponse> getAllActive() {
        return convoyRepository.findAllByIsActiveTrue(Sort.by(Sort.Direction.ASC, "id"))
                .stream()
                .map(convoy -> modelMapper.map(convoy, ConvoyResponse.class))
                .toList();
    }

    @Override
    public ConvoyResponse getById(Integer convoyId) {
        Convoy convoy = convoyRepository.findById(convoyId)
                .orElseThrow(() -> new BusinessException(HttpStatus.CONFLICT, "CONVOY-3"));
        return modelMapper.map(convoy, ConvoyResponse.class);
    }

    @Override
    public ConvoyResponse updateById(Integer convoyId, ConvoyRequest convoyRequest) {
        ConvoyResponse convoyFromDb = getById(convoyId);
        if (!convoyFromDb.getName().equals(convoyRequest.getName())) {
            if (convoyRepository.findByNameAndIsActiveFalse(convoyRequest.getName()).isEmpty()) {
                checkIfNameInUse(convoyRequest.getName());
            } else {
                throw new BusinessException(HttpStatus.CONFLICT, "CONVOY-2");
            }
        } else {
            return convoyFromDb;
        }
        Convoy convoyForUpdate = modelMapper.map(convoyRequest, Convoy.class);
        convoyForUpdate.setId(convoyId);
        convoyForUpdate.setIsActive(convoyFromDb.getIsActive());
        Convoy updatedConvoy = convoyRepository.save(convoyForUpdate);
        return modelMapper.map(updatedConvoy, ConvoyResponse.class);
    }

    @Override
    public void deleteById(Integer convoyId) {
        ConvoyResponse convoyFromDb = getById(convoyId);
        if (!convoyFromDb.getIsActive()) {
            throw new BusinessException(HttpStatus.CONFLICT, "CONVOY-4");
        } else {
            convoyFromDb.setIsActive(false);
            convoyRepository.save(modelMapper.map(convoyFromDb, Convoy.class));
        }
    }

    private void checkIfNameInUse(String name) {
        if (convoyRepository.findByName(name).isPresent()) {
            throw new BusinessException(HttpStatus.CONFLICT, "CONVOY-1");
        }
    }
}
