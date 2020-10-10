package com.transon.securityDemo.requestModel.role;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Data
public class RequestRoleModel {

    @NotEmpty(message = "name is required!")
    private String name;

    private String description;

    private Set<Long> menus = new HashSet<>();

    private boolean active = true;
}
