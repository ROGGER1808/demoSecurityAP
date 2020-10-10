package com.transon.securityDemo.repositories;

import com.transon.securityDemo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    Set<Role> findRolesByNameIn(Set<String> roleNames);

    @Transactional
    void deleteRoleByName(String name);

    @Query("DELETE FROM Role ba WHERE ba.id = :id")
    void deleteRoleRaw(@Param("id") Long id);
}
