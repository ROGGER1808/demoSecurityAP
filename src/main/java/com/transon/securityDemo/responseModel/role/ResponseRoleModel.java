package com.transon.securityDemo.responseModel.role;

import com.transon.securityDemo.responseModel.Menu.ResponseMenuModel;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class ResponseRoleModel {

    private Long id;

    private String name;

    private String description;

    private Set<ResponseMenuModel> menus = new HashSet<>();
}
