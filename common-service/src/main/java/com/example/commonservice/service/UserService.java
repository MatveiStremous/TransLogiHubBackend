package com.example.commonservice.service;

import com.example.commonservice.dto.UpdateUserRequest;
import com.example.commonservice.dto.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAll();

    UserResponse update(String login, UpdateUserRequest updateUserRequest);

    UserResponse getByLogin(String login);

    void deleteByLogin(String login);

    List<UserResponse> getAllActive();
}
