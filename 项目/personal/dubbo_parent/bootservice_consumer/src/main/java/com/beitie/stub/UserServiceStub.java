package com.beitie.stub;

import com.beitie.base.bean.User;
import com.beitie.base.service.UserService;

import java.util.List;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/5/22
 */
public class UserServiceStub implements UserService {
    private final UserService userService;

    public UserServiceStub(UserService userService) {
        super();
        this.userService = userService;
    }

    @Override
    public List<User> findUserByUid(String uid) {
        try {
            System.out.println("预处理参数");
            return userService.findUserByUid("");
        }catch (Exception e){
            System.out.println("返回异常情况");
        }
        return null;
    }
}
