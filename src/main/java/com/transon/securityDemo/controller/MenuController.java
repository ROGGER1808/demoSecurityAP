package com.transon.securityDemo.controller;

import com.transon.securityDemo.entity.Menu;
import com.transon.securityDemo.entity.Role;
import com.transon.securityDemo.entity.User;
import com.transon.securityDemo.exceptions.NotFoundEntityException;
import com.transon.securityDemo.mapper.MenuMapper;
import com.transon.securityDemo.requestModel.menu.RequestMenuModel;
import com.transon.securityDemo.responseModel.Menu.ResponseMenuModel;
import com.transon.securityDemo.responseModel.Menu.ResponseMenus;
import com.transon.securityDemo.responseModel.ResponseMessage;
import com.transon.securityDemo.services.IMenuService;
import com.transon.securityDemo.services.IUserService;
import com.transon.securityDemo.utils.JwtUtil;
import com.transon.securityDemo.utils.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/management/menus")
public class MenuController {

    private final IMenuService menuService;
    private final JwtUtil jwtUtil;
    private final IUserService userService;

    public MenuController(IMenuService menuService, JwtUtil jwtUtil, IUserService userService) {
        this.menuService = menuService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(menuService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create( @RequestBody @Valid RequestMenuModel menuRq){
        Menu menu = menuService.findByUrl(menuRq.getUrl());
        if (menu != null) {
            return new ResponseEntity<>("menu url already exist!", HttpStatus.BAD_REQUEST);
        }
        Menu menu1 = MenuMapper.INSTANCE.menuRequestToMenu(menuRq);
        return new ResponseEntity<>(menuService.save(menu1), HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id){
        Menu menu = menuService.findById(id)
                .orElseThrow(() -> new NotFoundEntityException(id, "Menu"));

        Set<Menu> menuSet = menuService.findMenusByParentId(id);
        Set<ResponseMenuModel> childsMenu = menuSet.stream().map(menu1 -> MenuMapper.INSTANCE.menuToMenuResponse(menu1)).collect(Collectors.toSet());
        ResponseMenuModel menuResponse = MenuMapper.INSTANCE.menuToMenuResponse(menu);
        Utils.sortMenu(childsMenu);
        menuResponse.setChilds(childsMenu);
        return  new ResponseEntity<>(menuResponse, HttpStatus.OK);
    }

    @GetMapping("byToken")
    public ResponseEntity<?> getMenuByToken(@RequestHeader("Authorization") String header){

        if (header.isEmpty() && !StringUtils.hasText(header)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String token = header.replace("Bearer ", "");

        String username = jwtUtil.extractUsername(token);
        User user = userService.findByUsername(username);
        Set<Role> roles = user.getRoles();

        Set<ResponseMenuModel> menus = Utils.getMenus(roles);

        return new ResponseEntity<>(new ResponseMenus(menus), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody RequestMenuModel menuRequest){

        Menu menu = menuService.findById(id)
                .map(menu1 -> {
                    menu1.setName(menuRequest.getName());
                    menu1.setOrderIndex(menuRequest.getOrderIndex());
                    menu1.setActive(menuRequest.isActive());
                    menu1.setParentId(menuRequest.getParentId());
                    menu1.setUrl(menuRequest.getUrl());
                    return menuService.save(menu1);
                })
                .orElseThrow(() -> new NotFoundEntityException(id, "Menu"));

        return  new ResponseEntity<>(menu, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>  delete(@PathVariable Long id){
        Menu menu = menuService.findById(id)
                .orElseThrow(() -> new NotFoundEntityException(id, "Menu"));
        menuService.delete(menu);
        return  new ResponseEntity<>(new ResponseMessage("deleted!"), HttpStatus.OK);
    }

}
