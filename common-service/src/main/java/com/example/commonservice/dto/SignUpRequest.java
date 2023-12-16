package com.example.commonservice.dto;

import com.example.commonservice.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    private String login;
    private Role role;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phone;
    private Integer convoyId;
}
