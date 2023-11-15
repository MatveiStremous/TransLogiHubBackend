package com.example.userservice.service.impl;


import com.example.userservice.dto.AuthRequest;
import com.example.userservice.dto.AuthResponse;
import com.example.userservice.dto.ChangePasswordRequest;
import com.example.userservice.dto.SignUpRequest;
import com.example.userservice.exception.BusinessException;
import com.example.userservice.model.User;
import com.example.userservice.model.enums.Role;
import com.example.userservice.security.JWTUtil;
import com.example.userservice.service.AuthService;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final String LOGIN_ALREADY_REGISTERED = "This login is already registered.";
    private final String WRONG_LOGIN_OR_PASSWORD = "Wrong login or password.";
    private final String WRONG_OLD_PASSWORD = "Wrong old password.";
    private final String USER_DOES_NOT_EXIST = "User with this login doesn't exist.";

    @Override
    public AuthResponse signup(SignUpRequest signUpRequest) {
        User user = User.builder()
                .login(signUpRequest.getLogin())
                .password(signUpRequest.getPassword())
                .phone(signUpRequest.getPhone())
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .middleName(signUpRequest.getMiddleName())
                .role(Role.ROLE_MANAGER)
                .isActive(true)
                .build();
        Optional<User> userFromDB = userService.findByLogin(user.getLogin());
        if (userFromDB.isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userService.save(user);
            String token = jwtUtil.generateAccessToken(savedUser.getLogin());
            return new AuthResponse(savedUser.getLogin(), token);
        } else {
            throw new BusinessException(HttpStatus.CONFLICT, LOGIN_ALREADY_REGISTERED);
        }
    }

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPassword());

        try {
            authenticationManager.authenticate(authToken);
        } catch (BadCredentialsException e) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, WRONG_LOGIN_OR_PASSWORD);
        }

        String token = jwtUtil.generateAccessToken(authRequest.getLogin());
        return new AuthResponse(authRequest.getLogin(), token);
    }

    @Override
    public void changePassword(ChangePasswordRequest changePasswordRequest, String fullJwtToken) {
        String login = jwtUtil.getLoginFromToken(fullJwtToken);
        User user = userService.findByLogin(login)
                .orElseThrow(() -> new BusinessException(HttpStatus.CONFLICT, USER_DOES_NOT_EXIST));

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getLogin(), changePasswordRequest.getOldPassword());
        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new BusinessException(HttpStatus.CONFLICT, WRONG_OLD_PASSWORD);
        }
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userService.update(user.getId(), user);
    }
}
