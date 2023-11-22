package com.example.commonservice.service.impl;

import com.example.commonservice.model.User;
import com.example.commonservice.repository.UserRepository;
import com.example.commonservice.service.AuthUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthUserServiceImpl implements AuthUserService {
    private final UserRepository userRepository;

    @Override
    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(Integer userId, User updatedUser) {
        updatedUser.setId(userId);
        return userRepository.save(updatedUser);
    }
}

