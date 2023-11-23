package com.example.commonservice.service.impl;

import com.example.commonservice.dto.UpdateUserRequest;
import com.example.commonservice.dto.UserResponse;
import com.example.commonservice.exception.BusinessException;
import com.example.commonservice.model.User;
import com.example.commonservice.repository.UserRepository;
import com.example.commonservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    private final String USER_DOES_NOT_EXIST = "User with this login doesn't exist.";
    private final String USER_IS_ALREADY_BLOCKED = "User is already blocked.";

    @Override
    public List<UserResponse> getAll() {
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .toList();
    }

    @Override
    public UserResponse update(String login, UpdateUserRequest updateUserRequest) {
        User userFromDb = getUserEntityByLogin(login);
        userFromDb.setPhone(updateUserRequest.getPhone());
        userFromDb.setFirstName(updateUserRequest.getFirstName());
        userFromDb.setLastName(updateUserRequest.getLastName());
        userFromDb.setMiddleName(updateUserRequest.getMiddleName());
        User updatedUser = userRepository.save(userFromDb);
        return modelMapper.map(updatedUser, UserResponse.class);
    }

    @Override
    public UserResponse getByLogin(String login) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new BusinessException(HttpStatus.CONFLICT, USER_DOES_NOT_EXIST));
        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public User getByLoginWithId(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new BusinessException(HttpStatus.CONFLICT, USER_DOES_NOT_EXIST));
    }

    @Override
    public void deleteByLogin(String login) {
        User userFromDb = getUserEntityByLogin(login);
        if (!userFromDb.getIsActive()) {
            throw new BusinessException(HttpStatus.CONFLICT, USER_IS_ALREADY_BLOCKED);
        } else {
            userFromDb.setIsActive(false);
            userRepository.save(userFromDb);
        }
    }

    @Override
    public List<UserResponse> getAllActive() {
        return userRepository.findAllByIsActiveTrue()
                .stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .toList();
    }

    private User getUserEntityByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new BusinessException(HttpStatus.CONFLICT, USER_DOES_NOT_EXIST));
    }
}
