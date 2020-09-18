package com.transon.securityDemo.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "username is required!")
    @Column(unique = true, nullable = false, length = 100)
    private String username;

    @NotNull(message = "password is required!")
    @Column(nullable = false)
    private String password;

    @Email(message = "Invalid email!")
    @NotNull(message = "password is required!")
    @Column(unique = true, nullable = false, length = 200)
    private String email;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "userRole",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

}
