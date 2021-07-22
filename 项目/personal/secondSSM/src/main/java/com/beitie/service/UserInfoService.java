package com.beitie.service;

import com.beitie.pojo.UserInfo;

import java.util.List;

public interface UserInfoService {
    UserInfo findUserInfoById(int id);
    List<UserInfo> findAllUsers();
}
