package com.beitie.lygdp_normal.service;

import com.beitie.lygdp_normal.bean.User;

import java.util.List;

public interface UserService {
    User selectUserById(Integer i);
    List<User> selectAllUser();
    void addUser(User user);
    void updateUser(User user);
    void deleteUser(Integer i);
    void updateUsersForDeleteUsers(Integer ids);
    List<User> selectAllUserAndOrders();
    List<User> findUsersByRelation();
}
