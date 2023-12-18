package com.example.commonservice.service;

import com.example.commonservice.dto.ReportResponse;

import java.time.LocalDate;

public interface ReportService {
    ReportResponse getConvoysTransportationsNumberByDates(LocalDate start, LocalDate end);

    ReportResponse getConvoysTransportationsSuccessByDates(LocalDate start, LocalDate end);
}
