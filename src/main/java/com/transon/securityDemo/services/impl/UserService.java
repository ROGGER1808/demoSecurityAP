package com.transon.securityDemo.services.impl;

import com.transon.securityDemo.entity.Department;
import com.transon.securityDemo.entity.RefreshToken;
import com.transon.securityDemo.entity.Role;
import com.transon.securityDemo.entity.User;
import com.transon.securityDemo.exceptions.NotFoundEntityException;
import com.transon.securityDemo.mapper.UserMapper;
import com.transon.securityDemo.repositories.UserRepository;
import com.transon.securityDemo.requestModel.User.RequestUpdateUser;
import com.transon.securityDemo.responseModel.User.ResponseUserDetail;
import com.transon.securityDemo.services.IDepartmentService;
import com.transon.securityDemo.services.IRoleService;
import com.transon.securityDemo.services.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final IRoleService roleService;
    private final IDepartmentService departmentService;

    public UserService(UserRepository userRepository, IRoleService roleService, IDepartmentService departmentService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.departmentService = departmentService;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findAll(Sort sort) {
        return userRepository.findAll(sort);
    }

    @Override
    public User update(RequestUpdateUser userRq, Long id) {
        Set<Role> roles = roleService.findRolesByNameIn(userRq.getRoles());
        Set<Department> ddepartments = departmentService.findDepartmentsByDepartmentCodeIn(userRq.getRoles());
        User user = userRepository.findById(id)
                .map(user1 -> {
                    user1.setRoles(roles);
                    user1.setDepartments(ddepartments);
                    user1.setPassword(userRq.getPassword());
                    user1.setAvatar(userRq.getAvatar());
                    user1.setEmail(userRq.getEmail());
                    user1.setFullname(userRq.getFullname());
                    user1.setPhone(userRq.getPhone());
                    return userRepository.save(user1);
                })
                .orElseThrow(() -> new NotFoundEntityException(id, "User"));
        return user;
    }

    @Override
    public <S extends User> S save(S s) {
        return userRepository.save(s);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean existsUserByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    @Override
    public boolean existsUserByUsername(String username) {
        return userRepository.existsUserByUsername(username);
    }

    @Override
    public User findUserByRefreshToken(RefreshToken refreshToken) {
        return userRepository.findUserByRefreshToken(refreshToken);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Page<User> findAllByFullnameContaining(String title, Pageable pageable) {
        return userRepository.findAllByFullnameContaining(title, pageable);
    }

    @Override
    public ResponseUserDetail userToUserResponse(User user) {

        Set<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
        ResponseUserDetail userResponse = UserMapper.INSTANCE.UserToUserDetail(user);
        userResponse.setRoles(roles);

        return userResponse;
    }


}
