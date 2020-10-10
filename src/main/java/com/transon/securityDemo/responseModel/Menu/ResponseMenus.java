package com.transon.securityDemo.responseModel.Menu;

import java.util.Set;


public class ResponseMenus {
    private Set<ResponseMenuModel> data;

    public ResponseMenus() {
    }

    public ResponseMenus(Set<ResponseMenuModel> menus) {
        this.data = menus;
    }

    public Set<ResponseMenuModel> getData() {
        return data;
    }

    public void setData(Set<ResponseMenuModel> data) {
        this.data = data;
    }
}
