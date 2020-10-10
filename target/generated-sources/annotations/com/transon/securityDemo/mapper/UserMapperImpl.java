package com.transon.securityDemo.mapper;

import com.transon.securityDemo.entity.Role;
import com.transon.securityDemo.entity.User;
import com.transon.securityDemo.requestModel.auth.RequestRegisterModel;
import com.transon.securityDemo.responseModel.User.ResponseUserDetail;
import com.transon.securityDemo.responseModel.User.ResponseUserInfor;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-10-11T00:24:56+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_265 (AdoptOpenJDK)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public User UserRequestToUser(RequestRegisterModel registerModel) {
        if ( registerModel == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( registerModel.getUsername() );
        user.setPassword( registerModel.getPassword() );
        user.setEmail( registerModel.getEmail() );
        user.setAvatar( registerModel.getAvatar() );
        user.setFullname( registerModel.getFullname() );
        user.setPhone( registerModel.getPhone() );
        Set<Role> set = registerModel.getRoles();
        if ( set != null ) {
            user.setRoles( new HashSet<Role>( set ) );
        }

        return user;
    }

    @Override
    public ResponseUserInfor UserToUserInfor(User user) {
        if ( user == null ) {
            return null;
        }

        ResponseUserInfor responseUserInfor = new ResponseUserInfor();

        responseUserInfor.setId( user.getId() );
        responseUserInfor.setUsername( user.getUsername() );
        responseUserInfor.setEmail( user.getEmail() );
        responseUserInfor.setAvatar( user.getAvatar() );
        responseUserInfor.setFullname( user.getFullname() );
        responseUserInfor.setPhone( user.getPhone() );

        return responseUserInfor;
    }

    @Override
    public ResponseUserDetail UserToUserDetail(User user) {
        if ( user == null ) {
            return null;
        }

        ResponseUserDetail responseUserDetail = new ResponseUserDetail();

        responseUserDetail.setId( user.getId() );
        responseUserDetail.setUsername( user.getUsername() );
        responseUserDetail.setEmail( user.getEmail() );
        responseUserDetail.setAvatar( user.getAvatar() );
        responseUserDetail.setFullname( user.getFullname() );
        responseUserDetail.setPhone( user.getPhone() );
        responseUserDetail.setCreatedAt( user.getCreatedAt() );
        responseUserDetail.setUpdatedAt( user.getUpdatedAt() );

        return responseUserDetail;
    }
}
