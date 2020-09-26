package com.transon.securityDemo.services;

import com.transon.securityDemo.auth.MyUserDetail;
import com.transon.securityDemo.auth.MyUserDetailService;
import com.transon.securityDemo.entity.RefreshToken;
import com.transon.securityDemo.entity.Role;
import com.transon.securityDemo.entity.User;
import com.transon.securityDemo.exceptions.MessageException;
import com.transon.securityDemo.exceptions.TokenRefreshException;
import com.transon.securityDemo.jwt.UsernameAndPasswordAuthenticationRequest;
import com.transon.securityDemo.mapper.UserMapper;
import com.transon.securityDemo.repositories.RoleRepository;
import com.transon.securityDemo.repositories.UserRepository;
import com.transon.securityDemo.requestModel.RequestRegisterModel;
import com.transon.securityDemo.requestModel.RequestUpdatePasswordModel;
import com.transon.securityDemo.responseModel.ResponseMessage;
import com.transon.securityDemo.responseModel.TokenResponse;
import com.transon.securityDemo.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final MyUserDetailService userDetailService;
    private final JwtUtil jwtTokenUtil;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager, MyUserDetailService userDetailService, JwtUtil jwtTokenUtil, RefreshTokenService refreshTokenService, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userDetailService = userDetailService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.refreshTokenService = refreshTokenService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> authenticate(UsernameAndPasswordAuthenticationRequest authenticationRequest) throws Exception {
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

        final String token = jwtTokenUtil.generateToken(userDetails);
        if (userDetailService.getUser() == null) {
            throw new MessageException("Error userService!");
        }
        RefreshToken refreshToken = refreshTokenService.createRefreshToken();
        refreshToken.setUser(userDetailService.getUser());
        refreshTokenService.save(refreshToken);
        return ResponseEntity.ok(new TokenResponse(token, refreshToken.getToken()));
    }

    public ResponseEntity<?> register(RequestRegisterModel registerModel) {

        if (userRepository.existsUserByEmail(registerModel.getEmail())
                || userRepository.existsUserByUsername(registerModel.getUsername())) {

            return new ResponseEntity<>(new ResponseMessage("username or email  already exist!"),
                    HttpStatus.BAD_REQUEST);

        }

        Role role = roleRepository.findByName("USER");
        Set<Role> roles = registerModel.getRoles();
        roles.add(role);
        registerModel.setRoles(roles);
        registerModel.setPassword(passwordEncoder.encode(registerModel.getPassword()));
        User user = UserMapper.INSTANCE.UserRequestToUser(registerModel);
        userRepository.save(user);
        return new ResponseEntity<>(new ResponseMessage("success!"), HttpStatus.OK);
    }

    public ResponseEntity<?> updatePassword(RequestUpdatePasswordModel passwordModel) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                                    .getAuthentication().getPrincipal();
        userDetails.getUsername();
        if (!passwordEncoder.matches(passwordModel.getCurrentPassword(), userDetails.getPassword())){
            new ResponseEntity<>(new ResponseMessage("wrong currentPassword!!"),
                    HttpStatus.BAD_REQUEST);
        }else if (!passwordModel.getCurrentPassword().equals(passwordModel.getConfirmNewPassword())){
            new ResponseEntity<>(new ResponseMessage("newPassword and confirmNewPassword not matches!!"),
                    HttpStatus.BAD_REQUEST);
        }
        String newPass = passwordEncoder.encode(passwordModel.getNewPassword());
        User user = userRepository.findByUsername(userDetails.getUsername());
        user.setPassword(newPass);
        userRepository.save(user);
        return ResponseEntity.ok(new ResponseMessage("success!"));
    }

    public ResponseEntity<?> refreshToken(String refreshToken) {

        Optional<RefreshToken> rftoken = Optional.of(refreshTokenService.findByToken(refreshToken)
                .map(rfToken -> {
                    refreshTokenService.verifyExpiration(rfToken);
                    return rfToken;
                }).orElseThrow(() -> new TokenRefreshException(refreshToken,
                        "Missing refresh token in database.Please login again")));
        User user = userRepository.findUserByRefreshToken(rftoken.get());
        MyUserDetail myUserDetail = new MyUserDetail(user);
        String token = jwtTokenUtil.generateToken(myUserDetail);
        return ResponseEntity.ok(new TokenResponse(token, refreshToken));
    }

}
