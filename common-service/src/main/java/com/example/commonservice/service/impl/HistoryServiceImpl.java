package com.example.commonservice.service.impl;

import com.example.commonservice.dto.HistoryResponse;
import com.example.commonservice.model.History;
import com.example.commonservice.model.enums.HistoryType;
import com.example.commonservice.repository.HistoryRepository;
import com.example.commonservice.security.JWTUtil;
import com.example.commonservice.service.HistoryService;
import com.example.commonservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {
    private final HistoryRepository historyRepository;
    private final ModelMapper modelMapper;
    private final JWTUtil jwtUtil;
    private final UserService userService;

    @Override
    public HistoryResponse add(Integer entityId, HistoryType type, String message) {
        History history = historyRepository.save(History.builder()
                .id(null)
                .entityId(entityId)
                .dateTime(LocalDateTime.now())
                .type(type)
                .message(message)
                .userId(getUserIdFromContext())
                .build());
        return modelMapper.map(history, HistoryResponse.class);
    }

    @Override
    public List<HistoryResponse> getAllByEntityIdAndType(Integer entityId, HistoryType type) {
        return historyRepository.findAllByEntityIdAndType(entityId, type)
                .stream()
                .map(history -> modelMapper.map(history, HistoryResponse.class))
                .toList();
    }

    private Integer getUserIdFromContext() {
        String login = jwtUtil.getClaimFromToken("login");
        if (login == null) {
            return null;
        }
        return userService.getByLogin(login).getId();
    }
}
