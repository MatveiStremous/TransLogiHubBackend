package com.example.commonservice.service;

import com.example.commonservice.dto.AuthRequest;
import com.example.commonservice.dto.AuthResponse;
import com.example.commonservice.dto.ChangePasswordRequest;
import com.example.commonservice.dto.SignUpRequest;
import com.example.commonservice.dto.UserResponse;

public interface AuthService {
    UserResponse signup(SignUpRequest signUpRequest);

    AuthResponse login(AuthRequest authRequest);

    void changePassword(ChangePasswordRequest changePasswordRequest, String fullJwtToken);
}
