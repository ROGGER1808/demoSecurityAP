package com.transon.securityDemo.services.impl;

import com.transon.securityDemo.entity.Menu;
import com.transon.securityDemo.entity.Role;
import com.transon.securityDemo.exceptions.NotFoundEntityException;
import com.transon.securityDemo.mapper.MenuMapper;
import com.transon.securityDemo.mapper.RoleMapper;
import com.transon.securityDemo.repositories.RoleRepository;
import com.transon.securityDemo.requestModel.role.RequestRoleModel;
import com.transon.securityDemo.responseModel.Menu.ResponseMenuModel;
import com.transon.securityDemo.responseModel.role.ResponseRoleModel;
import com.transon.securityDemo.services.IMenuService;
import com.transon.securityDemo.services.IRoleService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService implements IRoleService {

    private final RoleRepository roleRepository;
    private final IMenuService  menuService;

    public RoleService(RoleRepository roleRepository, IMenuService menuService) {
        this.roleRepository = roleRepository;
        this.menuService = menuService;
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public List<Role> findAll(Sort sort) {
        return roleRepository.findAll(sort);
    }

    @Override
    public Role save(RequestRoleModel roleModel) {

        Set<Menu> menus = menuService.findMenusByIdIn(roleModel.getMenus());
        Role role = RoleMapper.INSTANCE.requestRoleModelToRole(roleModel);
        role.setMenus(menus);

        return roleRepository.save(role);
    }

    @Override
    public ResponseRoleModel update(RequestRoleModel roleModel, Long id) {


        Role roleResponse = roleRepository.findById(id).map(role -> {

            role.setName(roleModel.getName());
            role.setDescription(roleModel.getDescription());
            role.setActive(roleModel.isActive());

            if (roleModel.getMenus() != null) {
                role.setMenus(menuService.findMenusByIdIn(roleModel.getMenus()));
            }

            return roleRepository.save(role);
        }).orElseThrow(() -> new NotFoundEntityException(id, "Role"));

        ResponseRoleModel responseRoleModel = RoleMapper.INSTANCE.RoleToResponseRoleModel(roleResponse);

        Set<ResponseMenuModel> menus = roleResponse.getMenus().stream()
                .map(MenuMapper.INSTANCE::menuToMenuResponse).collect(Collectors.toSet());

        responseRoleModel.setMenus(menus);
        return responseRoleModel;
    }

    @Override
    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public void delete(Role role) {
        roleRepository.delete(role);
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Set<Role> findRolesByNameIn(Set<String> roleNames) {
        return roleRepository.findRolesByNameIn(roleNames);
    }

    @Override
    public void deleteRoleByName(String name) {
        roleRepository.deleteRoleByName(name);
    }

    @Override
    public void deleteRoleRaw(Long id) {
        roleRepository.deleteRoleRaw(id);
    }
}
