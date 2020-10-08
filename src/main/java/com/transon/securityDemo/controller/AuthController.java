package com.transon.securityDemo.controller;


import com.transon.securityDemo.entity.User;
import com.transon.securityDemo.jwt.UsernameAndPasswordAuthenticationRequest;
import com.transon.securityDemo.requestModel.*;
import com.transon.securityDemo.services.AuthService;
import com.transon.securityDemo.services.IUserService;
import com.transon.securityDemo.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final IUserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, PasswordEncoder passwordEncoder,
                          IUserService userService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
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

    @CrossOrigin(origins = "*")
    @PutMapping
    public ResponseEntity<?> update(@RequestHeader (name="Authorization") String token,
                                    @Valid @RequestBody RequestUserSefUpdate userRq){

        String username = null;

        if (token != null && token.startsWith("Bearer ")){
            token= token.substring(7);
            username = jwtUtil.extractUsername(token);
        }

        User user = userService.findByUsername(username);
        if (user != null){
            user.setPassword(userRq.getPassword());
            user.setAvatar(userRq.getAvatar());
            user.setEmail(userRq.getEmail());
            user.setFullname(userRq.getFullname());
            user.setPhone(userRq.getPhone());
            user.setPassword(passwordEncoder.encode(userRq.getPassword()));
            return  new ResponseEntity<>(userService.save(user), HttpStatus.OK);
        }

        return  new ResponseEntity<>("error update!", HttpStatus.OK);
    }

}
