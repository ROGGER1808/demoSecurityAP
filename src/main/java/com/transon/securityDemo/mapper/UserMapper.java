package com.transon.securityDemo.mapper;

import com.transon.securityDemo.entity.User;
import com.transon.securityDemo.requestModel.RequestRegisterModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    User UserRequestToUser(RequestRegisterModel registerModel);
}
