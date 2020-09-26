package com.transon.securityDemo.requestModel;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RequestRefreshModel {
    @NotEmpty(message = "refreshToken is required!")
    private String refreshToken;
}
