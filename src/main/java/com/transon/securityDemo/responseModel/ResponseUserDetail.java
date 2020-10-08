package com.transon.securityDemo.responseModel;

import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class ResponseUserDetail {
    private Long id;

    private String username;

    private String email;

    private String avatar;

    private String fullname;

    private String phone;

    private Date createdAt;

    private Date updatedAt;

    private String createdBy;

    private String updatedBy;

    private Boolean isActive;

    private Set<String> roles;
}
