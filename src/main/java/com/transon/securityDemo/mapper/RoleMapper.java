package com.transon.securityDemo.mapper;

import com.transon.securityDemo.entity.Role;
import com.transon.securityDemo.requestModel.role.RequestRoleModel;
import com.transon.securityDemo.responseModel.role.ResponseItemRoleModel;
import com.transon.securityDemo.responseModel.role.ResponseRoleModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    Set<ResponseItemRoleModel> rolesToRoleDTO(Set<Role> roles);

    @Mappings({
            @Mapping(target = "name", source = "role.name"),
            @Mapping(target = "description", source = "role.description"),
    })
    ResponseItemRoleModel roleToRoleItem(Role role);


    @Mapping(target = "menus", ignore = true)
    Role requestRoleModelToRole(RequestRoleModel roleModel);


    @Mapping(target = "menus", ignore = true)
    ResponseRoleModel RoleToResponseRoleModel(Role role);
}
