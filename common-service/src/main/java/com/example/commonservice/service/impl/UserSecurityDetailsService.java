package com.example.commonservice.service.impl;

import com.example.commonservice.model.User;
import com.example.commonservice.repository.UserRepository;
import com.example.commonservice.security.UserSecurityDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserSecurityDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final String USER_NOT_FOUND = "User not found!";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByLogin(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(USER_NOT_FOUND);
        }
        return new UserSecurityDetails(user.get());
    }
}
