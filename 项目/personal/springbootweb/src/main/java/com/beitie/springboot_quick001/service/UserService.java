package com.beitie.springboot_quick001.service;

import com.beitie.springboot_quick001.entity.User;

import java.util.Map;

public interface UserService {

    Map<String,Object>  getUserLockTime(String username);
    long getLoginFailureCount(String username);
    boolean login(String userName,String password);
    void setUserLockTime(String username);
    long getUserLockTimeRemain(String username);
    void addUser(User user);
    void getUserById(int id);
}
