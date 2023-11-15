package com.example.userservice.dto;

import com.example.userservice.model.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private String login;
    private Role role;
    private Boolean isActive;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phone;
}
