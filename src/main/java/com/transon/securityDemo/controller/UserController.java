package com.transon.securityDemo.controller;

import com.transon.securityDemo.entity.Role;
import com.transon.securityDemo.entity.User;
import com.transon.securityDemo.exceptions.NotFoundEntityException;
import com.transon.securityDemo.requestModel.RequestUpdateUser;
import com.transon.securityDemo.responseModel.ResponseMessage;
import com.transon.securityDemo.services.IRoleService;
import com.transon.securityDemo.services.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/management/users")
public class UserController {

    private final IUserService userService;
    private final IRoleService roleService;

    public UserController(IUserService userService, IRoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long id){
        User user = userService.findById(id)
                .orElseThrow(() -> new NotFoundEntityException(id, "Product"));

        return  new ResponseEntity<>(user, HttpStatus.OK);
    }

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

            Map<String, Object> response = new HashMap<>();
            response.put("data", users);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody RequestUpdateUser userRequest){

        User user = userService.findById(id)
                .map(user1 -> {
                    if (userRequest.getRoleId() != null){
                        Role result = roleService.findById(id)
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
                    return userService.save(user1);
                })
                .orElseThrow(() -> new NotFoundEntityException(id, "User"));

        return  new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){

        User user = userService.findById(id)
                .orElseThrow(() -> new NotFoundEntityException(id, "User"));
        user.setActive(false);
        userService.save(user);

        return  new ResponseEntity<>(new ResponseMessage("deleted!"), HttpStatus.OK);
    }

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }
}


