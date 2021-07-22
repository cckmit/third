package com.beitie.service.impl;

import com.beitie.base.bean.User;
import com.beitie.base.service.UserService;
import com.beitie.service.OrderService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/5/20
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Reference(version = "1.0.0" ,check = false,stub = "com.beitie.stub.UserServiceStub")
    UserService userService;

    public List<User>     initData(){
        List<User> list=userService.findUserByUid("uid");
        for (User user : list) {
            System.out.println(user.getName()+"----------"+user.getAddress());
        }
        return list;
    }
}
