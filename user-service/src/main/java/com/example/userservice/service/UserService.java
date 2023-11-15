package com.example.userservice.service;

import com.example.userservice.dto.UpdateUserRequest;
import com.example.userservice.dto.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAll();

    UserResponse update(String login, UpdateUserRequest updateUserRequest);

    UserResponse getByLogin(String login);

    void deleteByLogin(String login);

    List<UserResponse> getAllActive();
}
