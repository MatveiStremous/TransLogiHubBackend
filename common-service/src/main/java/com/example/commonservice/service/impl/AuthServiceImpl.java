package com.example.commonservice.service.impl;


import com.example.commonservice.dto.AuthRequest;
import com.example.commonservice.dto.AuthResponse;
import com.example.commonservice.dto.ChangePasswordRequest;
import com.example.commonservice.dto.SignUpRequest;
import com.example.commonservice.dto.UserResponse;
import com.example.commonservice.exception.BusinessException;
import com.example.commonservice.kafka.MailProducer;
import com.example.commonservice.kafka.dto.MailKafkaDto;
import com.example.commonservice.model.User;
import com.example.commonservice.security.JWTUtil;
import com.example.commonservice.security.PasswordGenerator;
import com.example.commonservice.service.AuthService;
import com.example.commonservice.service.AuthUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final AuthUserService authUserService;
    private final PasswordEncoder passwordEncoder;
    private final PasswordGenerator passwordGenerator;
    private final MailProducer mailProducer;
    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final Integer TOKEN_START_POSITION = 7;

    @Override
    public UserResponse signup(SignUpRequest signUpRequest) {
        User user = User.builder()
                .login(signUpRequest.getLogin().toLowerCase())
                .password(passwordGenerator.generatePassword())
                .phone(signUpRequest.getPhone())
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .middleName(signUpRequest.getMiddleName())
                .convoyId(signUpRequest.getConvoyId())
                .role(signUpRequest.getRole())
                .isActive(true)
                .build();
        Optional<User> userFromDB = authUserService.findByLogin(user.getLogin());
        if (userFromDB.isEmpty()) {
            MailKafkaDto mailKafkaDto = MailKafkaDto.builder()
                    .consumerEmail(user.getLogin())
                    .topicName("Успешная регистрация")
                    .message("Вы были зарегистрированы в TransLogiHub.\nВаш логин: "
                            + user.getLogin() + "\nВаш пароль: " + user.getPassword())
                    .build();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = authUserService.save(user);

            mailProducer.sendMailKafkaDtoToConsumer(mailKafkaDto);

            return modelMapper.map(savedUser, UserResponse.class);
        } else {
            throw new BusinessException(HttpStatus.CONFLICT, "AUTH-2");
        }
    }

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authRequest.getLogin().toLowerCase(), authRequest.getPassword());

        try {
            authenticationManager.authenticate(authToken);
        } catch (BadCredentialsException e) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "AUTH-1");
        }
        User user = authUserService.findByLogin(authRequest.getLogin())
                .orElseThrow(() -> new BusinessException(HttpStatus.UNAUTHORIZED, "AUTH-1"));

        String token = jwtUtil.generateAccessToken(user);
        return new AuthResponse(authRequest.getLogin(), token);
    }

    @Override
    public void changePassword(ChangePasswordRequest changePasswordRequest, String fullJwtToken) {
        String token = fullJwtToken.substring(TOKEN_START_POSITION);
        String login = jwtUtil.getClaimFromToken(token, "login");
        User user = authUserService.findByLogin(login)
                .orElseThrow(() -> new BusinessException(HttpStatus.CONFLICT, "USER-1"));

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getLogin(), changePasswordRequest.getOldPassword());
        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new BusinessException(HttpStatus.CONFLICT, "AUTH-3");
        }
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        authUserService.update(user.getId(), user);
    }
}
