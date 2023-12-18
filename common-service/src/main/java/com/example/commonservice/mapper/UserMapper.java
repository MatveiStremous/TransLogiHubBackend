package com.example.commonservice.mapper;

import com.example.commonservice.dto.ConvoyResponse;
import com.example.commonservice.dto.UserResponse;
import com.example.commonservice.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ModelMapper mapper;

    public UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .middleName(user.getMiddleName())
                .phone(user.getPhone())
                .login(user.getLogin())
                .id(user.getId())
                .isActive(user.getIsActive())
                .role(user.getRole())
                .convoy(user.getConvoy() == null ? null : mapper.map(user.getConvoy(), ConvoyResponse.class))
                .build();
    }
}
