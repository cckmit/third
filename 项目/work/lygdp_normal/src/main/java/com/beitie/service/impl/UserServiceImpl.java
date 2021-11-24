package com.beitie.lygdp_normal.service.impl;

import com.beitie.lygdp_normal.bean.User;
import com.beitie.lygdp_normal.mapper.UserMapper;
import com.beitie.lygdp_normal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findUsersByRelation() {
        return userMapper.findUsersByRelation();
    }

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

    @Override
    public List<User> selectAllUserAndOrders() {
        return userMapper.selectAllUserAndOrders();
    }
}
