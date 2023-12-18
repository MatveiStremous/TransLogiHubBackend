package com.example.commonservice.service.impl;


import com.example.commonservice.dto.ReportResponse;
import com.example.commonservice.dto.TransportationResponse;
import com.example.commonservice.dto.UserResponse;
import com.example.commonservice.model.enums.TransportationStatus;
import com.example.commonservice.service.ReportService;
import com.example.commonservice.service.TransportationService;
import com.example.commonservice.service.UserService;
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
    private final UserService userService;

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

    @Override
    public ReportResponse getTransportationsStatuses() {
        List<TransportationResponse> transportationsInRange = transportationService.getAll().stream()
                .filter(transportation ->
                        transportation.getStatus() != TransportationStatus.COMPLETED &&
                                transportation.getStatus() != TransportationStatus.CANCELED)
                .toList();

        Map<String, Integer> transportationsStatuses = transportationsInRange.stream()
                .collect(Collectors.groupingBy(
                        transportation -> transportation.getStatus().toString(),
                        Collectors.summingInt(t -> 1)
                ));
        return ReportResponse.builder()
                .report(transportationsStatuses)
                .build();
    }

    @Override
    public ReportResponse getUsersConvoyNumber() {
        List<UserResponse> users = userService.getAll().stream()
                .filter(UserResponse::getIsActive)
                .toList();
        Map<String, Integer> usersConvoy = users.stream()
                .collect(Collectors.groupingBy(
                        user -> user.getConvoy().getName(),
                        Collectors.summingInt(t -> 1)
                ));
        return ReportResponse.builder()
                .report(usersConvoy)
                .build();
    }

    private boolean isTransportationSuccessful(TransportationResponse transportation) {
        LocalDateTime unloadingDate = transportation.getOrder().getUnloadingDate();
        LocalDateTime dateOfUnloading = transportation.getDateOfUnloading();

        return dateOfUnloading != null && unloadingDate != null && dateOfUnloading.isBefore(unloadingDate);
    }
}
