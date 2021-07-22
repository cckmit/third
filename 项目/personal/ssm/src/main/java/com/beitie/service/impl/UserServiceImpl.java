package com.beitie.service.impl;

import com.beitie.bean.User;
import com.beitie.mapper.UserMapper;
import com.beitie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void addUserInfo(User user) {
        userMapper.addUserInfo(user);
    }

    @Override
    public List<User> findAllUsers() {
        return userMapper.findAllUsers();
    }

    @Override
    public User findUserInfoById(int id) {
        return userMapper.findUserInfoById(id);
    }

    @Override
    public void addUserInfos(List<User> users) {
        userMapper.addUserInfos(users);
    }
}
