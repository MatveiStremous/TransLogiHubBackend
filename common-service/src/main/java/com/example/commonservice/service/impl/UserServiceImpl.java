package com.example.commonservice.service.impl;

import com.example.commonservice.dto.UpdateUserRequest;
import com.example.commonservice.dto.UserInfoResponse;
import com.example.commonservice.dto.UserResponse;
import com.example.commonservice.exception.BusinessException;
import com.example.commonservice.model.User;
import com.example.commonservice.repository.UserRepository;
import com.example.commonservice.security.JWTUtil;
import com.example.commonservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JWTUtil jwtUtil;
    private final Integer TOKEN_START_POSITION = 7;

    @Override
    public List<UserResponse> getAll() {
        return userRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))
                .stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .toList();
    }

    @Override
    public UserResponse update(String fullJwtToken, UpdateUserRequest updateUserRequest) {
        String token = fullJwtToken.substring(TOKEN_START_POSITION);
        String login = jwtUtil.getClaimFromToken(token, "login");
        User userFromDb = getEntityByLogin(login);
        userFromDb.setPhone(updateUserRequest.getPhone());
        userFromDb.setFirstName(updateUserRequest.getFirstName());
        userFromDb.setLastName(updateUserRequest.getLastName());
        userFromDb.setMiddleName(updateUserRequest.getMiddleName());
        User updatedUser = userRepository.save(userFromDb);
        return modelMapper.map(updatedUser, UserResponse.class);
    }

    @Override
    public UserResponse updateById(Integer userId, UpdateUserRequest updateUserRequest) {
        User userFromDb = getEntityById(userId);
        userFromDb.setPhone(updateUserRequest.getPhone());
        userFromDb.setFirstName(updateUserRequest.getFirstName());
        userFromDb.setLastName(updateUserRequest.getLastName());
        userFromDb.setMiddleName(updateUserRequest.getMiddleName());
        User updatedUser = userRepository.save(userFromDb);
        return modelMapper.map(updatedUser, UserResponse.class);
    }

    @Override
    public UserResponse getByLogin(String login) {
        return modelMapper.map(getEntityByLogin(login.toLowerCase()), UserResponse.class);
    }

    @Override
    public UserResponse getById(Integer id) {
        return modelMapper.map(getEntityById(id), UserResponse.class);
    }

    @Override
    public UserInfoResponse getInfoById(Integer id) {
        return modelMapper.map(getEntityById(id), UserInfoResponse.class);
    }

    @Override
    public User getEntityById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.CONFLICT, "USER-2"));
    }

    @Override
    public User getEntityByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new BusinessException(HttpStatus.CONFLICT, "USER-1"));
    }

    @Override
    public List<UserResponse> getAllActive() {
        return userRepository.findAllByIsActiveTrue()
                .stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .toList();
    }

    @Override
    public UserResponse blockUser(Integer userId) {
        User userFromDb = getEntityById(userId);
        if (!userFromDb.getIsActive()) {
            throw new BusinessException(HttpStatus.CONFLICT, "USER-3");
        } else {
            userFromDb.setIsActive(false);
            User updatedUser = userRepository.save(userFromDb);
            return modelMapper.map(updatedUser, UserResponse.class);
        }
    }

    @Override
    public UserResponse unblockUser(Integer userId) {
        User userFromDb = getEntityById(userId);
        if (userFromDb.getIsActive()) {
            throw new BusinessException(HttpStatus.CONFLICT, "USER-4");
        } else {
            userFromDb.setIsActive(true);
            User updatedUser = userRepository.save(userFromDb);
            return modelMapper.map(updatedUser, UserResponse.class);
        }
    }

    @Override
    public UserResponse getUserInfo(String fullJwtToken) {
        String token = fullJwtToken.substring(TOKEN_START_POSITION);
        String login = jwtUtil.getClaimFromToken(token, "login");
        return getByLogin(login);
    }
}
