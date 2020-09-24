package com.transon.securityDemo.controller;

import com.transon.securityDemo.entity.Role;
import com.transon.securityDemo.entity.User;
import com.transon.securityDemo.exceptions.NotFoundEntityException;
import com.transon.securityDemo.repositories.RoleRepository;
import com.transon.securityDemo.repositories.UserRepository;
import com.transon.securityDemo.requestModel.RequestUpdateUser;
import com.transon.securityDemo.responseModel.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/management/users")
public class UserController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException(id, "Product"));

        return  new ResponseEntity<>(user, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody RequestUpdateUser userRequest){

        User user = userRepository.findById(id)
                .map(user1 -> {
                    if (userRequest.getRoleId() != null){
                        Role result = roleRepository.findById(id)
                                .orElseThrow(() -> new NotFoundEntityException(userRequest.getRoleId(), "Role"));
                        if (!result.isActive()){
                            throw new NotFoundEntityException(userRequest.getRoleId(), "Role");
                        }
                        user1.getRoles().add(result);
                    }
                    user1.setPassword(userRequest.getPassword());
                    user1.setAvatar(userRequest.getAvatar());
                    user1.setEmail(userRequest.getEmail());
                    user1.setFullname(userRequest.getFullname());
                    user1.setPhone(userRequest.getPhone());
                    user1.setUpdatedAt(new Date());
                    return userRepository.save(user1);
                })
                .orElseThrow(() -> new NotFoundEntityException(id, "User"));

        return  new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException(id, "User"));
        user.setActive(false);
        userRepository.save(user);

        return  new ResponseEntity<>(new ResponseMessage("deleted!"), HttpStatus.OK);
    }

}
