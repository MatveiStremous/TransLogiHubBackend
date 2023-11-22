package com.example.commonservice.model;

import com.example.commonservice.model.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String login;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private Boolean isActive;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phone;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private Convoy convoy;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "driver")
    private List<Transportation> transportations;
}
