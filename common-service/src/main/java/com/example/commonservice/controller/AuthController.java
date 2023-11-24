package com.example.commonservice.controller;

import com.example.commonservice.dto.AuthRequest;
import com.example.commonservice.dto.AuthResponse;
import com.example.commonservice.dto.ChangePasswordRequest;
import com.example.commonservice.dto.SignUpRequest;
import com.example.commonservice.dto.UserResponse;
import com.example.commonservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("common/user/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        return authService.login(authRequest);
    }

    @PostMapping("/signup")
    public UserResponse register(@RequestBody SignUpRequest signUpRequest) {
        return authService.signup(signUpRequest);
    }

    @PutMapping("/password")
    public void changePassword(@RequestHeader("Authorization") String fullToken, @RequestBody ChangePasswordRequest changePasswordRequest) {
        authService.changePassword(changePasswordRequest, fullToken);
    }
}

