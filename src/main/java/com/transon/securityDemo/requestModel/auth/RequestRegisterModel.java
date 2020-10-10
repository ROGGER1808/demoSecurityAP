package com.transon.securityDemo.requestModel.auth;

import com.transon.securityDemo.entity.Role;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Data
public class RequestRegisterModel {
    @NotEmpty(message = "username is required!")
    private String username;

    @NotEmpty(message = "password is required!")
    private String password;

    @Email(message = "Invalid email!")
    @NotEmpty(message = "password is required!")
    private String email;

    private String avatar;

    @NotEmpty(message = "fullname is required!")
    private String fullname;

    @NotEmpty(message = "phone is required!")
    private String phone;

    private Set<Role> roles = new HashSet<>();
}
