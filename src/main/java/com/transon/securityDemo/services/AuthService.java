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
import com.transon.securityDemo.requestModel.auth.RequestRegisterModel;
import com.transon.securityDemo.requestModel.auth.RequestUpdatePasswordModel;
import com.transon.securityDemo.responseModel.ResponseMessage;
import com.transon.securityDemo.responseModel.User.ResponseUserInfor;
import com.transon.securityDemo.responseModel.auth.TokenResponse;
import com.transon.securityDemo.services.impl.UserService;
import com.transon.securityDemo.utils.JwtUtil;
import com.transon.securityDemo.utils.Utils;
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
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final MyUserDetailService userDetailService;
    private final JwtUtil jwtTokenUtil;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager, MyUserDetailService userDetailService, JwtUtil jwtTokenUtil,
                       RefreshTokenService refreshTokenService, UserService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userDetailService = userDetailService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.refreshTokenService = refreshTokenService;
        this.userService = userService;
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
        String username = jwtTokenUtil.extractUsername(token);
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new MessageException("Error userService!");
        }
        if (user.getRefreshToken() != null){
            refreshTokenService.deleteById(user.getRefreshToken().getId());
        }

        RefreshToken refreshToken = refreshTokenService.createRefreshToken();
        userService.save(user);
        refreshToken.setUser(user);
        refreshTokenService.save(refreshToken);

        ResponseUserInfor userInfor = UserMapper.INSTANCE.UserToUserInfor(user);
        Set<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
        userInfor.setRoles(roles);
        userInfor.setMenus(Utils.getMenus(user.getRoles()));
        return ResponseEntity.ok(new TokenResponse(token, refreshToken.getToken(), userInfor));
    }

    public ResponseEntity<?> register(RequestRegisterModel registerModel) {

        if (userService.existsUserByEmail(registerModel.getEmail())
                || userService.existsUserByUsername(registerModel.getUsername())) {

            return new ResponseEntity<>(new ResponseMessage("username or email  already exist!"),
                    HttpStatus.BAD_REQUEST);

        }
        registerModel.setPassword(passwordEncoder.encode(registerModel.getPassword()));
        User user = UserMapper.INSTANCE.UserRequestToUser(registerModel);
        userService.save(user);
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
        User user = userService.findByUsername(userDetails.getUsername());
        user.setPassword(newPass);
        userService.save(user);
        return ResponseEntity.ok(new ResponseMessage("success!"));
    }

    public ResponseEntity<?> refreshToken(String refreshToken) {

        Optional<RefreshToken> rftoken = Optional.of(refreshTokenService.findByToken(refreshToken)
                .map(rfToken -> {
                    refreshTokenService.verifyExpiration(rfToken);
                    return rfToken;
                }).orElseThrow(() -> new TokenRefreshException(refreshToken,
                        "Missing refresh token in database.Please login again")));
        User user = rftoken.get().getUser();
        if (user == null) {
            new ResponseEntity<>(new ResponseMessage("cannot find user with this refresh token!"),
                    HttpStatus.BAD_REQUEST);
        }
        MyUserDetail myUserDetail = new MyUserDetail(user);
        String token = jwtTokenUtil.generateToken(myUserDetail);
        ResponseUserInfor userInfor = UserMapper.INSTANCE.UserToUserInfor(user);
        return ResponseEntity.ok(new TokenResponse(token, refreshToken, userInfor));
    }

}
