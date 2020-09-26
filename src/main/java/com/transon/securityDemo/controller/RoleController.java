package com.transon.securityDemo.controller;

import com.transon.securityDemo.entity.Role;
import com.transon.securityDemo.exceptions.MessageException;
import com.transon.securityDemo.exceptions.NotFoundEntityException;
import com.transon.securityDemo.repositories.RoleRepository;
import com.transon.securityDemo.responseModel.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/management/roles")
public class RoleController {

    private final RoleRepository roleRepository;

    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @GetMapping
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(roleRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create( @RequestBody @Valid Role role){
        Role rolee = roleRepository.findByName(role.getName());
        if (rolee != null) {
            throw new MessageException("name already exist!");
        }
        roleRepository.save(role);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id){
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException(id, "Role"));

        return  new ResponseEntity<>(role, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Role roleRequest){

        Role role = roleRepository.findById(id)
                .map(role1 -> {
                    role1.setName(roleRequest.getName());
                    return roleRepository.save(role1);
                })
                .orElseThrow(() -> new NotFoundEntityException(id, "Role"));

        return  new ResponseEntity<>(role, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>  delete(@PathVariable Long id){
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException(id, "Role"));
        role.setActive(false);
        roleRepository.save(role);

        return  new ResponseEntity<>(new ResponseMessage("deleted!"), HttpStatus.OK);
    }
}
