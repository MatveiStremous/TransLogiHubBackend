package com.example.commonservice.dto;

import com.example.commonservice.model.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpResponse {
    private String login;
    private Role role;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phone;
}
