package com.example.commonservice.service;

import com.example.commonservice.dto.UpdateUserRequest;
import com.example.commonservice.dto.UserResponse;
import com.example.commonservice.model.User;

import java.util.List;

public interface UserService {
    List<UserResponse> getAll();

    UserResponse update(String fullJwtToken, UpdateUserRequest updateUserRequest);

    UserResponse getByLogin(String login);

    User getEntityById(Integer id);

    User getEntityByLogin(String login);

    List<UserResponse> getAllActive();

    UserResponse getById(Integer userId);

    UserResponse blockUser(Integer userId);

    UserResponse unblockUser(Integer userId);
}
