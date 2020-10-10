package com.transon.securityDemo.requestModel.menu;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class RequestMenuModel {

    @NotNull(message = "parentId is required!")
    private Long parentId;

    @NotEmpty(message = "name is required!")
    private String name;

    @NotEmpty(message = "url is required!")
    private String url;

    @NotNull(message = "orderIndex is required!")
    private Integer orderIndex;

    private boolean active = true;
}
