package com.transon.securityDemo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestRoleController {

    @RequestMapping("/admin")
    public ResponseEntity<?> get(){
        return new ResponseEntity<>("admin has access!", HttpStatus.OK);
    }

    @RequestMapping("/BAN")
    public ResponseEntity<?> get1(){
        return new ResponseEntity<>("admin has access!", HttpStatus.OK);
    }

    @RequestMapping("/BKT")
    public ResponseEntity<?> get2(){
        return new ResponseEntity<>("admin has access!", HttpStatus.OK);
    }

    @RequestMapping("/RCN")
    public ResponseEntity<?> get3(){
        return new ResponseEntity<>("admin has access!", HttpStatus.OK);
    }

    @RequestMapping("/CSR")
    public ResponseEntity<?> get4(){
        return new ResponseEntity<>("admin has access!", HttpStatus.OK);
    }

    @RequestMapping("/CUS")
    public ResponseEntity<?> get5(){
        return new ResponseEntity<>("admin has access!", HttpStatus.OK);
    }
}
