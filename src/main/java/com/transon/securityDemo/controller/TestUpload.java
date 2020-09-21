package com.transon.securityDemo.controller;

import com.transon.securityDemo.services.CloudDinaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/upload")
public class TestUpload {

    private final CloudDinaryService cloudDinaryService;

    public TestUpload(CloudDinaryService cloudDinaryService) {
        this.cloudDinaryService = cloudDinaryService;
    }

    @PostMapping
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file){
        return new ResponseEntity<>(cloudDinaryService.uploadFile(file), HttpStatus.OK);
    }
}
