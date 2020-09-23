package com.transon.securityDemo.controller;

import com.transon.securityDemo.auth.MyUserDetailService;
import com.transon.securityDemo.entity.Role;
import com.transon.securityDemo.entity.User;
import com.transon.securityDemo.jwt.UsernameAndPasswordAuthenticationRequest;
import com.transon.securityDemo.repositories.RoleRepository;
import com.transon.securityDemo.repositories.UserRepository;
import com.transon.securityDemo.responseModel.ResponseMessage;
import com.transon.securityDemo.responseModel.ResponseRegisterModel;
import com.transon.securityDemo.responseModel.TokenResponse;
import com.transon.securityDemo.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final JwtUtil jwtTokenUtil;
    final MyUserDetailService userDetailService;
    final AuthenticationManager authenticationManager;
    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;
    final RoleRepository roleRepository;

    public AuthController(JwtUtil jwtTokenUtil, AuthenticationManager authenticationManager,
                          MyUserDetailService userDetailService, UserRepository userRepository,
                          PasswordEncoder passwordEncoder, RoleRepository roleRepository) {

        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
        this.userDetailService = userDetailService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody @Valid  UsernameAndPasswordAuthenticationRequest authenticationRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password");
        }

        final UserDetails userDetails = userDetailService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new TokenResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody @Valid User user) throws Exception {

        if (userRepository.existsUserByEmail(user.getEmail())
                || userRepository.existsUserByUsername(user.getUsername())){

            return new ResponseEntity<>(new ResponseMessage("username or email  already exist!"),
                    HttpStatus.BAD_REQUEST);

        }

        Role role = roleRepository.findByName("USER");
        Set<Role> roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(new Date());
        userRepository.save(user);
        return ResponseEntity.ok(new ResponseRegisterModel("true"));
    }

}
