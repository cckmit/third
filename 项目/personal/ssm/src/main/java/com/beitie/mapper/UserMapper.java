package com.beitie.mapper;

import com.beitie.bean.User;

import java.util.List;

public interface UserMapper {
    List<User> findAllUsers();
    User findUserInfoById(int id);
    void addUserInfo(User user);
    void addUserInfos(List<User> user);
}
