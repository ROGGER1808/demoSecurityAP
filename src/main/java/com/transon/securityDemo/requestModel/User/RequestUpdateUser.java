package com.transon.securityDemo.requestModel.User;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
public class RequestUpdateUser {

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
    @Column(nullable = false, length = 250)
    private String phone;

    private Set<String> roles;
}
