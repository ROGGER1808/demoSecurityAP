package com.transon.securityDemo.mapper;

import com.transon.securityDemo.entity.User;
import com.transon.securityDemo.requestModel.RequestRegisterModel;
import com.transon.securityDemo.responseModel.ResponseUserInfor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    User UserRequestToUser(RequestRegisterModel registerModel);
    ResponseUserInfor UserToUserInfor(User user);
}
