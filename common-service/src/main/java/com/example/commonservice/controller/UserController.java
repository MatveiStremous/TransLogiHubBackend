package com.example.commonservice.controller;

import com.example.commonservice.dto.UpdateUserRequest;
import com.example.commonservice.dto.UserResponse;
import com.example.commonservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("common/user")
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
    public UserResponse updateUser(@RequestHeader("Authorization") String fullToken,
                                   @RequestBody UpdateUserRequest updateUserRequest) {
        return userService.update(fullToken, updateUserRequest);
    }

    @GetMapping("/login")
    public UserResponse getUserByLogin(@RequestParam String login) {
        return userService.getByLogin(login);
    }

    @GetMapping("/id")
    public UserResponse getUserByLogin(@RequestParam Integer userId) {
        return userService.getById(userId);
    }

    @PutMapping("/block")
    public UserResponse blockUser(@RequestParam Integer userId) {
        return userService.blockUser(userId);
    }

    @PutMapping("/unblock")
    public UserResponse unblockUser(@RequestParam Integer userId) {
        return userService.unblockUser(userId);
    }
}
