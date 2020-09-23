package com.transon.securityDemo.repositories;

import com.transon.securityDemo.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    boolean existsDepartmentByName(String name);
}
