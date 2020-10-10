package com.transon.securityDemo.services.impl;

import com.transon.securityDemo.entity.Menu;
import com.transon.securityDemo.repositories.MenuRepository;
import com.transon.securityDemo.services.IMenuService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class MenuService implements IMenuService {

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    @Override
    public List<Menu> findAll(Sort sort) {
        return menuRepository.findAll(sort);
    }

    @Override
    public <S extends Menu> S save(S s) {
        return menuRepository.save(s);
    }

    @Override
    public void deleteById(Long id) {
        menuRepository.deleteById(id);
    }

    @Override
    public void delete(Menu menu) {
        menuRepository.delete(menu);
    }

    @Override
    public Optional<Menu> findById(Long id) {
        return menuRepository.findById(id);
    }

    @Override
    public Menu findByName(String name) {
        return menuRepository.findByName(name);
    }

    @Override
    public Menu findByUrl(String url) {
        return menuRepository.findByUrl(url);
    }

    @Override
    public Set<Menu> findMenusByParentId(Long parentId) {
        return menuRepository.findMenusByParentId(parentId);
    }

    @Override
    public Set<Menu> findMenusByIdIn(Set<Long> ids) {
        return menuRepository.findMenusByIdIn(ids);
    }


}
