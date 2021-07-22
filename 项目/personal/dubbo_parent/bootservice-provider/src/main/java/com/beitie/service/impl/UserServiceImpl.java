package com.beitie.service.impl;

import com.beitie.base.bean.User;
import com.beitie.base.service.UserService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/5/18
 */
@Service(version = "1.0.0")
@Component
public class UserServiceImpl implements UserService {
    @Override
    public List<User> findUserByUid(String uid) {
        User user =new User("001","jame","China");
        User user2 =new User("001","Bidden","USA");
        return Arrays.asList(user,user2);
    }
}
