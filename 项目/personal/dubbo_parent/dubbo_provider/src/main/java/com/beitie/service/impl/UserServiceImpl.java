package com.beitie.service.impl;

import com.beitie.base.bean.User;
import com.beitie.base.service.UserService;

import java.util.Arrays;
import java.util.List;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/5/18
 */
public class UserServiceImpl implements UserService {
    @Override
    public List<User> findUserByUid(String uid) {
        User user =new User("001","李四","China");
        User user2 =new User("001","张三","USA");
        return Arrays.asList(user,user2);
    }
}
