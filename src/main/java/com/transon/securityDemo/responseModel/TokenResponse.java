package com.transon.securityDemo.responseModel;

public class TokenResponse {
    private String tokenType = "Bearer ";
    private String accessToken;
    private String refreshToken;
    private ResponseUserInfor infor;

    public TokenResponse(String accessToken, String refreshToken , ResponseUserInfor userInfor) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.infor = userInfor;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public ResponseUserInfor getInfor() {
        return infor;
    }

    public void setInfor(ResponseUserInfor infor) {
        this.infor = infor;
    }
}
