package com.example.userservice.service;

import com.example.userservice.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByLogin(String login);
    User save(User user);
    User update(Integer userId, User updatedUser);
}
