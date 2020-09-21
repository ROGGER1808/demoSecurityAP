package com.transon.securityDemo.responseEntity;

public class ResponseUploadFile {
    private final String url;
    private final String publicId;

    public ResponseUploadFile(String url, String publicId) {
        this.url = url;
        this.publicId = publicId;
    }

    public String getUrl() {
        return url;
    }

    public String getPublicId() {
        return publicId;
    }
}
