package com.beitie.service;

import com.beitie.bean.User;

import java.util.List;

public interface UserService {
    User selectUserById(Integer i);
    List<User> selectAllUser();
    void addUser(User user);
    void updateUser(User user);
    void deleteUser(Integer i);
    void updateUsersForDeleteUsers(Integer ids);
}
