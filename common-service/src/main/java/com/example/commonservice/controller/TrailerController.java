package com.example.commonservice.controller;

import com.example.commonservice.dto.TrailerRequest;
import com.example.commonservice.dto.TrailerResponse;
import com.example.commonservice.service.TrailerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("common/transport/trailer")
@RequiredArgsConstructor
public class TrailerController {
    private final TrailerService trailerService;

    @PostMapping
    public TrailerResponse addTrailer(@RequestBody TrailerRequest trailerRequest) {
        return trailerService.add(trailerRequest);
    }

    @GetMapping
    public List<TrailerResponse> getAllTrailers() {
        return trailerService.getAll();
    }

    @GetMapping("/active")
    public List<TrailerResponse> getAllActiveTrailers() {
        return trailerService.getAllActive();
    }

    @PutMapping("/{trailerId}")
    public TrailerResponse updateTrailer(@PathVariable Integer trailerId,
                                         @RequestBody TrailerRequest trailerRequest) {
        return trailerService.update(trailerId, trailerRequest);
    }

    @GetMapping("/{trailerId}")
    public TrailerResponse getTrailerById(@PathVariable Integer trailerId) {
        return trailerService.getById(trailerId);
    }

    @DeleteMapping("/{trailerId}")
    public void deleteTrailer(@PathVariable Integer trailerId) {
        trailerService.deleteById(trailerId);
    }
}
