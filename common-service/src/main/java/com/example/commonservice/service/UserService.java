package com.example.commonservice.service;

import com.example.commonservice.dto.UpdateUserRequest;
import com.example.commonservice.dto.UserResponse;
import com.example.commonservice.model.User;

import java.util.List;

public interface UserService {
    List<UserResponse> getAll();

    UserResponse update(String login, UpdateUserRequest updateUserRequest);

    UserResponse getByLogin(String login);

    User getByLoginWithId(String login);

    void deleteByLogin(String login);

    List<UserResponse> getAllActive();
}
