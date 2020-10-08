package com.transon.securityDemo.controller;

import com.transon.securityDemo.entity.Role;
import com.transon.securityDemo.entity.User;
import com.transon.securityDemo.exceptions.NotFoundEntityException;
import com.transon.securityDemo.mapper.UserMapper;
import com.transon.securityDemo.requestModel.RequestUpdateUser;
import com.transon.securityDemo.responseModel.ResponseMessage;
import com.transon.securityDemo.responseModel.ResponseUserDetail;
import com.transon.securityDemo.responseModel.ResponseUserInfor;
import com.transon.securityDemo.services.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/management/users")
public class UserController {

    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(IUserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /*
    @getAll user
    @transon
    */
    @GetMapping
    public ResponseEntity<?> getAll(){
        List<ResponseUserDetail> dataResponse = userService.findAll().stream()
                .map(userService::userToUserResponse).collect(Collectors.toList());
        return ResponseEntity.ok(dataResponse);
    }


    /*
    @getUser by id
    @transon
    */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long id){
        User user = userService.findById(id)
                .orElseThrow(() -> new NotFoundEntityException(id, "User"));

        Set<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
        ResponseUserInfor userResponse = UserMapper.INSTANCE.UserToUserInfor(user);
        userResponse.setRoles(roles);

        return  new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    /*
    @getAllFormat have sort and paging, filter
    @transon
    */
    @GetMapping("/format")
    public ResponseEntity<?> getAllFormat(
            @RequestParam(required = false) String filter,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "3") int pageSize,
            @RequestParam(defaultValue = "id,desc") String[] sort) {

        try {
            List<Order> orders = new ArrayList<Order>();

            if (sort[0].contains(",")) {
                // will sort more than 2 fields
                // sortOrder="field, direction"
                for (String sortOrder : sort) {
                    String[] _sort = sortOrder.split(",");
                    orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
                }
            } else {
                // sort=[field, direction]
                orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
            }

            List<User> users = new ArrayList<User>();
            Pageable pagingSort = PageRequest.of(pageNumber, pageSize, Sort.by(orders));

            Page<User> pageTuts;
            if (filter == null)
                pageTuts = userService.findAll(pagingSort);
            else
                pageTuts = userService.findAllByFullnameContaining(filter, pagingSort);

            users = pageTuts.getContent();

            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<ResponseUserDetail> dataResponse = users.stream()
                    .map(userService::userToUserResponse).collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("data", dataResponse);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /*
    @update method
    @transon
    */
    @CrossOrigin(origins = "*")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody RequestUpdateUser userRequest){
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        User user = userService.update(userRequest, id);
        Set<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
        ResponseUserInfor userResponse = UserMapper.INSTANCE.UserToUserInfor(user);
        userResponse.setRoles(roles);
        return  new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    /*
    @delete method
    @transon
    */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){

        User user = userService.findById(id)
                .orElseThrow(() -> new NotFoundEntityException(id, "User"));
        user.setActive(false);
        userService.save(user);

        return  new ResponseEntity<>(new ResponseMessage("deleted!"), HttpStatus.OK);
    }

    /*
    @getSortDirection utils method
    @transon
    */
    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }
}


