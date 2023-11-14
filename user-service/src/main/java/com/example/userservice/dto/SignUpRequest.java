package com.example.userservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phone;

}
