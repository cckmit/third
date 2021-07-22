package com.beitie.service;

import com.beitie.bean.User;

import java.util.List;

public interface UserService {
    List<User> findAllUsers();
    User findUserInfoById(int id);
    void addUserInfo(User user);
    void addUserInfos(List<User> user);
}
