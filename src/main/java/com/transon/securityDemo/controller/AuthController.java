package com.transon.securityDemo.controller;


import com.transon.securityDemo.jwt.UsernameAndPasswordAuthenticationRequest;
import com.transon.securityDemo.requestModel.RequestRefreshModel;
import com.transon.securityDemo.requestModel.RequestRegisterModel;
import com.transon.securityDemo.requestModel.RequestUpdatePasswordModel;
import com.transon.securityDemo.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
            @RequestBody @Valid UsernameAndPasswordAuthenticationRequest authenticationRequest) throws Exception {

        return authService.authenticate(authenticationRequest);
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody @Valid RequestRegisterModel registerModel) {

        return authService.register(registerModel);
    }


    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(
            @RequestBody @Valid RequestUpdatePasswordModel passwordModel) {

        return  authService.updatePassword(passwordModel);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(
            @RequestBody @Valid RequestRefreshModel refreshModel) {

        return  authService.refreshToken(refreshModel.getRefreshToken());
    }

}
