package com.example.commonservice.controller;

import com.example.commonservice.dto.TrailerTypeRequest;
import com.example.commonservice.dto.TrailerTypeResponse;
import com.example.commonservice.service.TrailerTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("common/transport/trailer/type")
@RequiredArgsConstructor
public class TrailerTypeController {
    private final TrailerTypeService trailerTypeService;

    @PostMapping
    public TrailerTypeResponse addTrailerType(@RequestBody TrailerTypeRequest trailerTypeRequest) {
        return trailerTypeService.add(trailerTypeRequest);
    }

    @GetMapping
    public List<TrailerTypeResponse> getTrailerTypes() {
        return trailerTypeService.getAll();
    }

    @GetMapping("/active")
    public List<TrailerTypeResponse> getActiveTrailerTypes() {
        return trailerTypeService.getAllActive();
    }

    @GetMapping("{trailerTypeId}")
    public TrailerTypeResponse getTrailerTypeById(@PathVariable Integer trailerTypeId) {
        return trailerTypeService.getById(trailerTypeId);
    }

    @PutMapping("{trailerTypeId}")
    public TrailerTypeResponse updateTrailerType(@PathVariable Integer trailerTypeId,
                                                 @RequestBody TrailerTypeRequest trailerTypeRequest) {
        return trailerTypeService.updateById(trailerTypeId, trailerTypeRequest);
    }

    @DeleteMapping("{trailerTypeId}")
    public void deleteTrailerType(@PathVariable Integer trailerTypeId) {
        trailerTypeService.deleteById(trailerTypeId);
    }
}
