package com.transon.securityDemo.mapper;

import com.transon.securityDemo.entity.Menu;
import com.transon.securityDemo.requestModel.menu.RequestMenuModel;
import com.transon.securityDemo.responseModel.Menu.ResponseMenuModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MenuMapper {

    MenuMapper INSTANCE = Mappers.getMapper(MenuMapper.class);

    ResponseMenuModel menuToMenuResponse(Menu menu);

    Menu menuRequestToMenu(RequestMenuModel menuModel);
}
