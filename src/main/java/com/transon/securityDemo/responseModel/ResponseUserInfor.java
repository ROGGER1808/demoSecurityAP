package com.transon.securityDemo.responseModel;

import lombok.Data;

import java.util.Set;

@Data
public class ResponseUserInfor {
    private Long id;

    private String username;

    private String email;

    private String avatar;

    private String fullname;

    private String phone;

    private Set<String> roles;
}
