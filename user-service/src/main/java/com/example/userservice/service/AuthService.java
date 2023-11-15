package com.example.userservice.service;

import com.example.userservice.dto.AuthRequest;
import com.example.userservice.dto.AuthResponse;
import com.example.userservice.dto.ChangePasswordRequest;
import com.example.userservice.dto.SignUpRequest;

public interface AuthService {
    AuthResponse signup(SignUpRequest signUpRequest);

    AuthResponse login(AuthRequest authRequest);

    void changePassword(ChangePasswordRequest changePasswordRequest, String fullJwtToken);
}
