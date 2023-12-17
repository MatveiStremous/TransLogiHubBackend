package com.example.commonservice.service.impl;

import com.example.commonservice.dto.TrailerInfoResponse;
import com.example.commonservice.dto.TrailerRequest;
import com.example.commonservice.dto.TrailerResponse;
import com.example.commonservice.exception.BusinessException;
import com.example.commonservice.model.Trailer;
import com.example.commonservice.model.enums.Role;
import com.example.commonservice.model.enums.TransportStatus;
import com.example.commonservice.repository.TrailerRepository;
import com.example.commonservice.security.JWTUtil;
import com.example.commonservice.service.TrailerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrailerServiceImpl implements TrailerService {
    private final TrailerRepository trailerRepository;
    private final ModelMapper modelMapper;
    private final JWTUtil jwtUtil;

    @Override
    public TrailerResponse add(TrailerRequest trailerRequest) {
        checkIfStateNumberInUse(trailerRequest.getStateNumber());
        Trailer trailer = modelMapper.map(trailerRequest, Trailer.class);
        trailer.setIsActive(true);
        trailer.setId(null);
        trailer.setStatus(TransportStatus.FREE);
        Trailer newTrailer = trailerRepository.save(trailer);
        return modelMapper.map(newTrailer, TrailerResponse.class);
    }

    @Override
    public List<TrailerResponse> getAll() {
        return trailerRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream()
                .map(trailer -> modelMapper.map(trailer, TrailerResponse.class))
                .toList();
    }

    @Override
    public List<TrailerResponse> getAllActive() {
        String role = jwtUtil.getClaimFromToken("role");
        if (role.equals(Role.ROLE_MANAGER.toString())) {
            return trailerRepository.findAllByIsActiveTrue(Sort.by(Sort.Direction.ASC, "id"))
                    .stream()
                    .map(trailer -> modelMapper.map(trailer, TrailerResponse.class))
                    .toList();
        } else {
            Integer convoyId = Integer.parseInt(jwtUtil.getClaimFromToken("convoyId"));
            return trailerRepository.findAllByIsActiveTrueAndConvoyId(convoyId, Sort.by(Sort.Direction.ASC, "id"))
                    .stream()
                    .map(trailer -> modelMapper.map(trailer, TrailerResponse.class))
                    .toList();
        }
    }

    @Override
    public TrailerResponse update(Integer id, TrailerRequest trailerRequest) {
        Trailer trailerFromDb = getEntityById(id);
        if (!trailerFromDb.getStateNumber().equals(trailerRequest.getStateNumber())) {
            checkIfStateNumberInUse(trailerRequest.getStateNumber());
        }
        Trailer trailerForUpdate = modelMapper.map(trailerRequest, Trailer.class);
        trailerForUpdate.setId(id);
        trailerForUpdate.setStatus(trailerFromDb.getStatus());
        trailerForUpdate.setIsActive(trailerFromDb.getIsActive());
        Trailer updatedTrailer = trailerRepository.save(trailerForUpdate);
        return modelMapper.map(updatedTrailer, TrailerResponse.class);
    }

    @Override
    public TrailerInfoResponse getById(Integer trailerId) {
        Trailer trailer = trailerRepository.findById(trailerId)
                .orElseThrow(() -> new BusinessException(HttpStatus.CONFLICT, "TRAILER-2"));
        return modelMapper.map(trailer, TrailerInfoResponse.class);
    }

    @Override
    public void deleteById(Integer trailerId) {
        Trailer trailerFromDb = getEntityById(trailerId);
        if (!trailerFromDb.getIsActive()) {
            throw new BusinessException(HttpStatus.CONFLICT, "TRAILER-3");
        } else {
            trailerFromDb.setIsActive(false);
            trailerRepository.save(modelMapper.map(trailerFromDb, Trailer.class));
        }
    }

    private void checkIfStateNumberInUse(String stateNumber) {
        if (trailerRepository.findByStateNumberAndIsActiveTrue(stateNumber).isPresent()) {
            throw new BusinessException(HttpStatus.CONFLICT, "TRAILER-1");
        }
    }

    private Trailer getEntityById(Integer id) {
        return trailerRepository.findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.CONFLICT, "TRAILER-2"));
    }
}
