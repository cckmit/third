package com.beitie.lygdp_normal.mapper;

import com.beitie.lygdp_normal.bean.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
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
