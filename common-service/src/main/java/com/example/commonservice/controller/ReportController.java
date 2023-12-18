package com.example.commonservice.controller;

import com.example.commonservice.dto.ReportResponse;
import com.example.commonservice.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("common/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/convoy/transportations/number")
    public List<ReportResponse> getConvoysTransportationsNumber(@RequestParam("start") LocalDate start,
                                                                @RequestParam("end") LocalDate end) {
        return reportService.getConvoysTransportationsNumberByDates(start, end);
    }

    @GetMapping("/convoy/transportations/success")
    public List<ReportResponse> getConvoysTransportationsSuccess(@RequestParam("start") LocalDate start,
                                                                 @RequestParam("end") LocalDate end) {
        return reportService.getConvoysTransportationsSuccessByDates(start, end);
    }

    @GetMapping("/transportations/status")
    public List<ReportResponse> getConvoysTransportationsSuccess() {
        return reportService.getTransportationsStatuses();
    }

    @GetMapping("/users/convoy/number")
    public List<ReportResponse> getUsersConvoyNumber() {
        return reportService.getUsersConvoyNumber();
    }
}
