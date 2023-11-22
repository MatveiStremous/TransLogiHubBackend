package com.example.userservice.controller;

import com.example.userservice.dto.UpdateUserRequest;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("/active")
    public List<UserResponse> getAllActiveUsers() {
        return userService.getAllActive();
    }

    @PutMapping
    public UserResponse updateUser(@RequestParam String login,
                                   @RequestBody UpdateUserRequest updateUserRequest) {
        return userService.update(login, updateUserRequest);
    }

    @GetMapping("/login")
    public UserResponse getUserByLogin(@RequestParam String login) {
        return userService.getByLogin(login);
    }

    @DeleteMapping
    public void deleteUser(@RequestParam String login) {
        userService.deleteByLogin(login);
    }
}
