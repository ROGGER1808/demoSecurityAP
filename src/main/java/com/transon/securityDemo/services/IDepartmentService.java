package com.transon.securityDemo.services;

import com.transon.securityDemo.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface IDepartmentService {

    List<Department> findAll();

    List<Department> findAll(Sort sort);

    <S extends Department> S save(S s);

    void deleteById(Long id);

    void delete(Department department);

    Optional<Department> findById(Long id);

    boolean existsDepartmentByName(String name);

    Page<Department> findAll(Pageable pageable);

    Page<Department> findAllByNameContaining(String title, Pageable pageable);
}
