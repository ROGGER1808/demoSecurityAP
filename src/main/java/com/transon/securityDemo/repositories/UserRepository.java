package com.transon.securityDemo.repositories;

import com.transon.securityDemo.entity.RefreshToken;
import com.transon.securityDemo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    boolean existsUserByEmail(String email);

    boolean existsUserByUsername(String username);

    User findUserByRefreshToken(RefreshToken refreshToken);

    Page<User> findAllByFullnameContaining(String title, Pageable pageable);
}
