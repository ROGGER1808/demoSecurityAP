package com.transon.securityDemo.mapper;

import com.transon.securityDemo.entity.Menu;
import com.transon.securityDemo.requestModel.menu.RequestMenuModel;
import com.transon.securityDemo.responseModel.Menu.ResponseMenuModel;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-10-11T20:27:14+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_265 (AdoptOpenJDK)"
)
public class MenuMapperImpl implements MenuMapper {

    @Override
    public ResponseMenuModel menuToMenuResponse(Menu menu) {
        if ( menu == null ) {
            return null;
        }

        ResponseMenuModel responseMenuModel = new ResponseMenuModel();

        responseMenuModel.setId( menu.getId() );
        responseMenuModel.setParentId( menu.getParentId() );
        responseMenuModel.setName( menu.getName() );
        responseMenuModel.setUrl( menu.getUrl() );
        responseMenuModel.setOrderIndex( menu.getOrderIndex() );
        responseMenuModel.setChilds( menuSetToResponseMenuModelSet( menu.getChilds() ) );

        return responseMenuModel;
    }

    @Override
    public Menu menuRequestToMenu(RequestMenuModel menuModel) {
        if ( menuModel == null ) {
            return null;
        }

        Menu menu = new Menu();

        menu.setActive( menuModel.isActive() );
        menu.setParentId( menuModel.getParentId() );
        menu.setName( menuModel.getName() );
        menu.setUrl( menuModel.getUrl() );
        if ( menuModel.getOrderIndex() != null ) {
            menu.setOrderIndex( menuModel.getOrderIndex() );
        }

        return menu;
    }

    protected Set<ResponseMenuModel> menuSetToResponseMenuModelSet(Set<Menu> set) {
        if ( set == null ) {
            return null;
        }

        Set<ResponseMenuModel> set1 = new HashSet<ResponseMenuModel>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Menu menu : set ) {
            set1.add( menuToMenuResponse( menu ) );
        }

        return set1;
    }
}
