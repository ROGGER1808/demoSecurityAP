package com.transon.securityDemo.services;

import com.transon.securityDemo.entity.RefreshToken;
import com.transon.securityDemo.entity.User;
import com.transon.securityDemo.requestModel.User.RequestUpdateUser;
import com.transon.securityDemo.responseModel.User.ResponseUserDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> findAll();

    List<User> findAll(Sort sort);
    User update(RequestUpdateUser user, Long id);

    <S extends User> S save(S s);

    void deleteById(Long id);

    void delete(User user);

    Optional<User> findById(Long id);

    User findByUsername(String username);

    boolean existsUserByEmail(String email);

    boolean existsUserByUsername(String username);

    User findUserByRefreshToken(RefreshToken refreshToken);

    Page<User> findAll(Pageable pageable);

    Page<User> findAllByFullnameContaining(String title, Pageable pageable);

    ResponseUserDetail userToUserResponse(User user);
}
