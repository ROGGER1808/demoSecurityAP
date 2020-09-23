package com.transon.securityDemo.responseModel;

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
