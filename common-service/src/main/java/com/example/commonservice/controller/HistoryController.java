package com.example.commonservice.controller;

import com.example.commonservice.dto.HistoryResponse;
import com.example.commonservice.model.enums.HistoryType;
import com.example.commonservice.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("common/history")
@RequiredArgsConstructor
public class HistoryController {
    private final HistoryService historyService;

    @GetMapping
    public List<HistoryResponse> getEntityHistory(@RequestParam("entityId") Integer entityId,
                                                  @RequestParam("type") HistoryType type) {
        return historyService.getAllByEntityIdAndType(entityId, type);
    }
}
