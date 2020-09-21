package com.transon.securityDemo.responseEntity;

import lombok.Data;

public class ResponseRegisterModel {
    private String success;

    public ResponseRegisterModel() {
    }

    public ResponseRegisterModel(String success) {
        this.success = success;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
