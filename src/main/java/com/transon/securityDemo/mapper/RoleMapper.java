package com.transon.securityDemo.mapper;

import com.transon.securityDemo.entity.Role;
import com.transon.securityDemo.responseModel.ResponseItemRoleModel;
import org.mapstruct.factory.Mappers;

import java.util.Set;

public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);
    Set<ResponseItemRoleModel> rolesToRoleDTO(Set<Role> roles);
}
