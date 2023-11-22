package com.example.commonservice.controller;

import com.example.commonservice.dto.ConvoyRequest;
import com.example.commonservice.dto.ConvoyResponse;
import com.example.commonservice.service.ConvoyService;
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
@RequestMapping("common/convoy")
@RequiredArgsConstructor
public class ConvoyController {
    private final ConvoyService convoyService;

    @PostMapping
    public ConvoyResponse addConvoy(@RequestBody ConvoyRequest convoyRequest) {
        return convoyService.add(convoyRequest);
    }

    @GetMapping
    public List<ConvoyResponse> getConvoys() {
        return convoyService.getAll();
    }

    @GetMapping("/active")
    public List<ConvoyResponse> getActiveConvoys() {
        return convoyService.getAllActive();
    }

    @GetMapping("{convoyId}")
    public ConvoyResponse getConvoyById(@PathVariable Integer convoyId) {
        return convoyService.getById(convoyId);
    }

    @PutMapping("{convoyId}")
    public ConvoyResponse updateConvoy(@PathVariable Integer convoyId,
                                                 @RequestBody ConvoyRequest convoyRequest) {
        return convoyService.updateById(convoyId, convoyRequest);
    }

    @DeleteMapping("{convoyId}")
    public void deleteConvoy(@PathVariable Integer convoyId) {
        convoyService.deleteById(convoyId);
    }
}
