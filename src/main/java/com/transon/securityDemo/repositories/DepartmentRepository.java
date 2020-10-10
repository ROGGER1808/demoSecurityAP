package com.transon.securityDemo.repositories;

import com.transon.securityDemo.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, String> {
    boolean existsDepartmentByName(String name);

    boolean existsDepartmentByDepartmentCode(String id);

    Page<Department> findAllByNameContaining(String title, Pageable pageable);

    Set<Department> findDepartmentsByDepartmentCodeIn(Set<String> departmentNames);

    Optional<Department> findDepartmentsByDepartmentCode(String code);

    @Transactional
    void deleteDepartmentByDepartmentCode(String code);
}
