package com.example.authservice.service;

import com.example.authservice.dto.AuthRequest;
import com.example.authservice.dto.AuthResponse;
import com.example.authservice.dto.SignUpRequest;
import com.example.authservice.model.User;

public interface AuthService {
    AuthResponse signup(SignUpRequest signUpRequest);

    AuthResponse login(AuthRequest authRequest);
}
