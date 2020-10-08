package com.transon.securityDemo.services;

import com.transon.securityDemo.entity.Role;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IRoleService {
    List<Role> findAll();

    List<Role> findAll(Sort sort);

    <S extends Role> S save(S s);

    void deleteById(Long id);

    void delete(Role role);

    Optional<Role> findById(Long id);

    Role findByName(String name);

    Set<Role> findRolesByNameIn(Set<String> roleNames);

    void deleteRoleByName(String name);

}
