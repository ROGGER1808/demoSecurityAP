package com.transon.securityDemo.mapper;

import com.transon.securityDemo.entity.User;
import com.transon.securityDemo.requestModel.auth.RequestRegisterModel;
import com.transon.securityDemo.responseModel.User.ResponseUserDetail;
import com.transon.securityDemo.responseModel.User.ResponseUserInfor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    User UserRequestToUser(RequestRegisterModel registerModel);


    @Mappings({
            @Mapping(target="id", source="user.id"),
            @Mapping(target = "roles", ignore = true),
    })
    ResponseUserInfor UserToUserInfor(User user);

    @Mappings({
            @Mapping(target="id", source="user.id"),
            @Mapping(target = "roles", ignore = true),
    })
    ResponseUserDetail UserToUserDetail(User user);
}
