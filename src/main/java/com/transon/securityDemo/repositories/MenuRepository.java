package com.transon.securityDemo.repositories;

import com.transon.securityDemo.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    Menu findByName(String name);
    Menu findByUrl(String url);
    Set<Menu> findMenusByParentId(Long parentId);
    Set<Menu> findMenusByIdIn(Set<Long> ids);
}
