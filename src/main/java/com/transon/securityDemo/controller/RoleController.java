package com.transon.securityDemo.controller;

import com.transon.securityDemo.entity.Role;
import com.transon.securityDemo.exceptions.MessageException;
import com.transon.securityDemo.exceptions.NotFoundEntityException;
import com.transon.securityDemo.repositories.RoleRepository;
import com.transon.securityDemo.responseModel.ResponseMessage;
import com.transon.securityDemo.services.IRoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/management/roles")
public class RoleController {

    private final IRoleService roleService;
    private final RoleRepository roleRepository;

    public RoleController(IRoleService roleService, RoleRepository roleRepository) {
        this.roleService = roleService;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(roleService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create( @RequestBody @Valid Role role){
        Role rolee = roleService.findByName(role.getName());
        if (rolee != null) {
            throw new MessageException("name already exist!");
        }
        roleService.save(role);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id){
        Role role = roleService.findById(id)
                .orElseThrow(() -> new NotFoundEntityException(id, "Role"));

        return  new ResponseEntity<>(role, HttpStatus.OK);
    }

    @GetMapping("/demo")
    public ResponseEntity<?> getDemo(){
        Set<String> names = new HashSet<>();
        names.add("ADMIN");
        names.add("DNSA");
        Set<Role> roles = roleRepository.findRolesByNameIn(names);

        return  new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Role roleRequest){

        Role role = roleService.findById(id)
                .map(role1 -> {
                    role1.setName(roleRequest.getName());
                    return roleService.save(role1);
                })
                .orElseThrow(() -> new NotFoundEntityException(id, "Role"));

        return  new ResponseEntity<>(role, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>  delete(@PathVariable Long id){
        Role role = roleService.findById(id)
                .orElseThrow(() -> new NotFoundEntityException(id, "Role"));
        role.setActive(false);
        roleService.save(role);

        return  new ResponseEntity<>(new ResponseMessage("deleted!"), HttpStatus.OK);
    }
}
