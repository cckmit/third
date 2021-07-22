package com.beitie.service.impl;

import com.beitie.bean.User;
import com.beitie.mapper.UserMapper;
import com.beitie.service.UserService;
import com.beitie.util.exception.BusinessRuntimeException;
import com.beitie.util.exception.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public List<User> selectAllUser(){
//        throw new BusinessRuntimeException(ResultCode.USER_NOTFOUND);
        return userMapper.selectAllUser();
    }

    @Override
    public User selectUserById(Integer i) {
        return userMapper.selectUserById(i);
    }

    @Override
    public void addUser(User user) {
        userMapper.addUser(user);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @Override
    public void deleteUser(Integer i) {
        userMapper.deleteUser(i);
    }

    @Override
    public void updateUsersForDeleteUsers(Integer ids) {
        userMapper.updateUsersForDeleteUsers(ids);
    }
}
