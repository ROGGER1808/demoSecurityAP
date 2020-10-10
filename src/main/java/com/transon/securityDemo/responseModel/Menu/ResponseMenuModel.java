package com.transon.securityDemo.responseModel.Menu;

import lombok.Data;
import java.util.Set;

@Data
public class ResponseMenuModel {

    private Long id;

    private Long parentId;

    private String name;

    private String url;

    private int orderIndex;

    private Set<ResponseMenuModel> childs;
}
