package com.beitie.dao;

import com.beitie.pojo.UserInfo;

import java.util.List;

public interface UserInfoMapper {
    UserInfo findUserInfoById(int id);
    List<UserInfo> findAllUsers();
}
