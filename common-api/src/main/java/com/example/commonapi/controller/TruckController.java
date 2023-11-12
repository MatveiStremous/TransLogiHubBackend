package com.example.commonapi.controller;

import com.example.commonapi.dto.TruckRequest;
import com.example.commonapi.dto.TruckResponse;
import com.example.commonapi.service.TruckService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("common/transport/truck")
@RequiredArgsConstructor
public class TruckController {
    private final TruckService truckService;

    @PostMapping
    public TruckResponse addTruck(@RequestBody TruckRequest truckRequest) {
        return truckService.add(truckRequest);
    }

    @GetMapping
    public List<TruckResponse> getAllTrucks() {
        return truckService.getAll();
    }

    @GetMapping("/active")
    public List<TruckResponse> getAllActiveTrucks() {
        return truckService.getAllActive();
    }

    @PutMapping("/{truckId}")
    public TruckResponse updateTruck(@PathVariable Integer truckId,
                                     @RequestBody TruckRequest truckRequest) {
        return truckService.update(truckId, truckRequest);
    }

    @GetMapping("/{truckId}")
    public TruckResponse getTruckById(@PathVariable Integer truckId) {
        return truckService.getById(truckId);
    }

    @DeleteMapping("/{truckId}")
    public void deleteTruck(@PathVariable Integer truckId) {
        truckService.deleteById(truckId);
    }
}
