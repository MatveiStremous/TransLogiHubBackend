package com.example.commonservice.repository;

import com.example.commonservice.model.User;
import com.example.commonservice.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByLogin(String login);

    List<User> findAllByIsActiveTrue();

    List<User> findAllByIsActiveTrueAndRole(Role role);
}
