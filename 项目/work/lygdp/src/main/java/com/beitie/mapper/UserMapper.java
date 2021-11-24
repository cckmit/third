package com.beitie.mapper;

import com.beitie.bean.User;

import java.util.List;

public interface UserMapper {
    User selectUserById(Integer i);
    List<User> selectAllUser();
    void addUser(User user);
    void updateUser(User user);
    void deleteUser(Integer i);
    void updateUsersForDeleteUsers(Integer id);
    List<User> selectAllUserAndOrders();
    List<User> findUsersByRelation();

}
