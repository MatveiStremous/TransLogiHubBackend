package com.example.commonservice.service;

import com.example.commonservice.model.User;

import java.util.Optional;

public interface AuthUserService {
    Optional<User> findByLogin(String login);

    User save(User user);

    User update(Integer userId, User updatedUser);
}
