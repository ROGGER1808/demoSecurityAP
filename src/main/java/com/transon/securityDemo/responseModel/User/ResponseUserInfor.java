package com.transon.securityDemo.responseModel.User;

import com.transon.securityDemo.responseModel.Menu.ResponseMenuModel;
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

    private Set<ResponseMenuModel> menus;
}
