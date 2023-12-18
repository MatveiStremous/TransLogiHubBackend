package com.example.commonservice.service.impl;


import com.example.commonservice.dto.ReportResponse;
import com.example.commonservice.dto.TransportationResponse;
import com.example.commonservice.model.enums.TransportationStatus;
import com.example.commonservice.service.ConvoyService;
import com.example.commonservice.service.ReportService;
import com.example.commonservice.service.TransportationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final TransportationService transportationService;
    private final ConvoyService convoyService;

    @Override
    public ReportResponse getConvoysTransportationsNumberByDates(LocalDate start, LocalDate end) {
        List<TransportationResponse> transportations = transportationService.getAll();

        List<TransportationResponse> transportationsInRange = transportations.stream()
                .filter(transportation ->
                        transportation.getStatus() == TransportationStatus.COMPLETED &&
                                transportation.getDateOfUnloading().toLocalDate().isAfter(start) &&
                                transportation.getDateOfUnloading().toLocalDate().isBefore(end)
                )
                .toList();

        Map<String, Integer> convoyTransportationCount = transportationsInRange.stream()
                .collect(Collectors.groupingBy(
                        transportation -> transportation.getConvoy().getName(),
                        Collectors.collectingAndThen(
                                Collectors.counting(),
                                Long::intValue
                        )
                ));
        return ReportResponse.builder()
                .report(convoyTransportationCount)
                .build();
    }

    @Override
    public ReportResponse getConvoysTransportationsSuccessByDates(LocalDate start, LocalDate end) {
        List<TransportationResponse> transportations = transportationService.getAll();

        List<TransportationResponse> transportationsInRange = transportations.stream()
                .filter(transportation ->
                        transportation.getStatus() == TransportationStatus.COMPLETED &&
                                transportation.getDateOfUnloading().toLocalDate().isAfter(start) &&
                                transportation.getDateOfUnloading().toLocalDate().isBefore(end)
                )
                .toList();

        Map<String, List<TransportationResponse>> groupedByConvoy = transportationsInRange.stream()
                .collect(Collectors.groupingBy(transportation -> transportation.getConvoy().getName()));

        Map<String, Integer> successPercentageByConvoy = new HashMap<>();
        for (String convoyName : groupedByConvoy.keySet()) {
            List<TransportationResponse> convoyTransportations = groupedByConvoy.get(convoyName);

            long successfulCount = convoyTransportations.stream()
                    .filter(this::isTransportationSuccessful)
                    .count();

            int successPercentage = (int) Math.round((double) successfulCount / convoyTransportations.size() * 100);
            successPercentageByConvoy.put(convoyName, successPercentage);
        }

        return ReportResponse.builder()
                .report(successPercentageByConvoy)
                .build();
    }

    private boolean isTransportationSuccessful(TransportationResponse transportation) {
        LocalDateTime unloadingDate = transportation.getOrder().getUnloadingDate();
        LocalDateTime dateOfUnloading = transportation.getDateOfUnloading();

        return dateOfUnloading != null && unloadingDate != null && dateOfUnloading.isBefore(unloadingDate);
    }
}
