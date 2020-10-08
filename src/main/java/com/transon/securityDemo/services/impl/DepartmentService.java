package com.transon.securityDemo.services.impl;

import com.transon.securityDemo.entity.Department;
import com.transon.securityDemo.repositories.DepartmentRepository;
import com.transon.securityDemo.services.IDepartmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DepartmentService implements IDepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Override
    public List<Department> findAll(Sort sort) {
        return departmentRepository.findAll(sort);
    }

    @Override
    public <S extends Department> S save(S s) {
        return departmentRepository.save(s);
    }

    @Override
    public void deleteById(String id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public void delete(Department department) {
        departmentRepository.delete(department);
    }

    @Override
    public Optional<Department> findById(String id) {
        return departmentRepository.findById(id);
    }

    @Override
    public boolean existsDepartmentByName(String name) {
        return departmentRepository.existsDepartmentByName(name);
    }

    @Override
    public Page<Department> findAll(Pageable pageable) {
        return departmentRepository.findAll(pageable);
    }

    @Override
    public Page<Department> findAllByNameContaining(String title, Pageable pageable) {
        return departmentRepository.findAllByNameContaining(title, pageable);
    }

    @Override
    public Set<Department> findDepartmentsByDepartmentCodeIn(Set<String> departmentNames) {
        return departmentRepository.findDepartmentsByDepartmentCodeIn(departmentNames);
    }

    @Override
    public Optional<Department> findDepartmentsByDepartmentCode(String code) {
        return departmentRepository.findDepartmentsByDepartmentCode(code);
    }

    @Override
    public void deleteDepartmentByDepartmentCode(String code) {
        departmentRepository.deleteDepartmentByDepartmentCode(code);
    }
}
