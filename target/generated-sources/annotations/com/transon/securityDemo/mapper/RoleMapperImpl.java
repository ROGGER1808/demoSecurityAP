package com.transon.securityDemo.mapper;

import com.transon.securityDemo.entity.Role;
import com.transon.securityDemo.requestModel.role.RequestRoleModel;
import com.transon.securityDemo.responseModel.role.ResponseItemRoleModel;
import com.transon.securityDemo.responseModel.role.ResponseRoleModel;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-10-11T00:24:56+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_265 (AdoptOpenJDK)"
)
public class RoleMapperImpl implements RoleMapper {

    @Override
    public Set<ResponseItemRoleModel> rolesToRoleDTO(Set<Role> roles) {
        if ( roles == null ) {
            return null;
        }

        Set<ResponseItemRoleModel> set = new HashSet<ResponseItemRoleModel>( Math.max( (int) ( roles.size() / .75f ) + 1, 16 ) );
        for ( Role role : roles ) {
            set.add( roleToRoleItem( role ) );
        }

        return set;
    }

    @Override
    public ResponseItemRoleModel roleToRoleItem(Role role) {
        if ( role == null ) {
            return null;
        }

        ResponseItemRoleModel responseItemRoleModel = new ResponseItemRoleModel();

        responseItemRoleModel.setName( role.getName() );
        responseItemRoleModel.setDescription( role.getDescription() );

        return responseItemRoleModel;
    }

    @Override
    public Role requestRoleModelToRole(RequestRoleModel roleModel) {
        if ( roleModel == null ) {
            return null;
        }

        Role role = new Role();

        role.setActive( roleModel.isActive() );
        role.setName( roleModel.getName() );
        role.setDescription( roleModel.getDescription() );

        return role;
    }

    @Override
    public ResponseRoleModel RoleToResponseRoleModel(Role role) {
        if ( role == null ) {
            return null;
        }

        ResponseRoleModel responseRoleModel = new ResponseRoleModel();

        responseRoleModel.setId( role.getId() );
        responseRoleModel.setName( role.getName() );
        responseRoleModel.setDescription( role.getDescription() );

        return responseRoleModel;
    }
}
