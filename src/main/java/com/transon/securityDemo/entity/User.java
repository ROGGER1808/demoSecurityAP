package com.transon.securityDemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class User extends AbstractEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "username is required!")
    @Column(unique = true, nullable = false, length = 100)
    private String username;

    @NotEmpty(message = "password is required!")
    @Column(nullable = false)
    private String password;

    @Email(message = "Invalid email!")
    @NotEmpty(message = "password is required!")
    @Column(unique = true, nullable = false, length = 200)
    private String email;

    @Column(name = "avatar")
    private String avatar;

    @NotEmpty(message = "fullname is required!")
    @Column(nullable = false, length = 250)
    private String fullname;

    @NotEmpty(message = "phone is required!")
    @Column(nullable = false, length = 11)
    private String phone;

    @JsonIgnore
    @OneToOne( mappedBy = "user")
    private RefreshToken refreshToken;


    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "userRole",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

}
