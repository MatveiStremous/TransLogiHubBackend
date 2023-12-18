package com.example.commonservice.service;

import com.example.commonservice.dto.ReportResponse;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    List<ReportResponse> getConvoysTransportationsNumberByDates(LocalDate start, LocalDate end);

    List<ReportResponse> getConvoysTransportationsSuccessByDates(LocalDate start, LocalDate end);

    List<ReportResponse> getTransportationsStatuses();

    List<ReportResponse> getUsersConvoyNumber();
}
