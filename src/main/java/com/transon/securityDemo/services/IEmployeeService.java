package com.transon.securityDemo.services;

import com.transon.securityDemo.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface IEmployeeService {
    List<Employee> findAll();

    List<Employee> findAll(Sort sort);

    <S extends Employee> S save(S s);

    void deleteById(Long id);

    void delete(Employee employee);

    Optional<Employee> findById(Long id);

    Page<Employee> findAll(Pageable pageable);

    Page<Employee> findAllByFullnameContaining(String title, Pageable pageable);
}
