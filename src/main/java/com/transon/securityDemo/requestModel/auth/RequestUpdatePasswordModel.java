package com.transon.securityDemo.requestModel.auth;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RequestUpdatePasswordModel {
    @NotEmpty(message = "currentPassword is required!")
    private String currentPassword;

    @NotEmpty(message = "newPassword is required!")
    private String newPassword;

    @NotEmpty(message = "confirmNewPassword is required!")
    private String confirmNewPassword;
}
