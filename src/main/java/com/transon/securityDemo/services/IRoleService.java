package com.transon.securityDemo.services;

import com.transon.securityDemo.entity.Role;
import com.transon.securityDemo.requestModel.role.RequestRoleModel;
import com.transon.securityDemo.responseModel.role.ResponseRoleModel;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IRoleService {
    List<Role> findAll();

    List<Role> findAll(Sort sort);

    Role save(RequestRoleModel roleModel);

    ResponseRoleModel update(RequestRoleModel roleModel, Long id);

    void deleteById(Long id);

    void delete(Role role);

    Optional<Role> findById(Long id);

    Role findByName(String name);

    Set<Role> findRolesByNameIn(Set<String> roleNames);

    void deleteRoleByName(String name);

    void deleteRoleRaw(@Param("id") Long id);
}
