package com.transon.securityDemo.services;

import com.transon.securityDemo.entity.Menu;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IMenuService {
    List<Menu> findAll();

    List<Menu> findAll(Sort sort);

    <S extends Menu> S save(S s);

    void deleteById(Long id);

    void delete(Menu menu);

    Optional<Menu> findById(Long id);

    Menu findByName(String name);

    Menu findByUrl(String url);

    Set<Menu> findMenusByParentId(Long parentId);

    Set<Menu> findMenusByIdIn(Set<Long> ids);
}
