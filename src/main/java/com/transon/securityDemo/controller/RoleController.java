package com.transon.securityDemo.controller;

import com.transon.securityDemo.entity.Role;
import com.transon.securityDemo.exceptions.MessageException;
import com.transon.securityDemo.exceptions.NotFoundEntityException;
import com.transon.securityDemo.mapper.MenuMapper;
import com.transon.securityDemo.mapper.RoleMapper;
import com.transon.securityDemo.requestModel.role.RequestRoleModel;
import com.transon.securityDemo.responseModel.Menu.ResponseMenuModel;
import com.transon.securityDemo.responseModel.ResponseMessage;
import com.transon.securityDemo.responseModel.role.ResponseItemRoleModel;
import com.transon.securityDemo.responseModel.role.ResponseRoleModel;
import com.transon.securityDemo.services.IRoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/management/roles")
public class RoleController {

    private final IRoleService roleService;

    public RoleController(IRoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {

        Set<ResponseItemRoleModel> data = roleService.findAll().stream()
                .map(role -> new ResponseItemRoleModel(role.getId(), role.getName(), role.getDescription())).collect(Collectors.toSet());

        Map<String, Object> response = new HashMap<>();
        response.put("data", data);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid RequestRoleModel roleRq) {

        Role rolee = roleService.findByName(roleRq.getName());
        if (rolee != null) {
            throw new MessageException("name already exist!");
        }

        Role role = roleService.save(roleRq);

        ResponseRoleModel responseRoleModel = convertResponse(role);

        return new ResponseEntity<>(responseRoleModel, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {

        Role role = roleService.findById(id)
                .orElseThrow(() -> new NotFoundEntityException(id, "Role"));

        ResponseRoleModel responseRoleModel = convertResponse(role);

        return new ResponseEntity<>(responseRoleModel, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody RequestRoleModel roleRequest) {

        ResponseRoleModel responseRoleModel = roleService.update(roleRequest, id);

        return new ResponseEntity<>(responseRoleModel, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Role role = roleService.findById(id)
                .orElseThrow(() -> new NotFoundEntityException(id, "Role"));
        roleService.deleteById(id);
        return new ResponseEntity<>(new ResponseMessage("deleted!"), HttpStatus.OK);
    }

    public ResponseRoleModel convertResponse(Role role) {
        ResponseRoleModel responseRoleModel = RoleMapper.INSTANCE.RoleToResponseRoleModel(role);

        Set<ResponseMenuModel> menus = role.getMenus().stream()
                .map(MenuMapper.INSTANCE::menuToMenuResponse).collect(Collectors.toSet());

        responseRoleModel.setMenus(menus);
        return responseRoleModel;
    }
}
