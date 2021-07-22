package com.beitie;

import com.beitie.base.bean.User;
import com.beitie.base.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.List;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/5/19
 */
public class ProvideAppStart {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext ac=new ClassPathXmlApplicationContext("spring-dubbo.xml");
        UserService userService=ac.getBean(UserService.class);
        List<User> uid = userService.findUserByUid("uid");
        for (User user : uid) {
            System.out.println(user.getAddress()+"************************");
        }
        System.in.read();
    }
}
