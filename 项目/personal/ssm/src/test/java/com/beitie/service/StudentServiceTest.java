package com.beitie.service;

import com.beitie.bean.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/springframework-dao.xml")
public class StudentServiceTest {
@Autowired
    private UserService userService;

    @Test
    public void findAllUsersTest(){
        List<User> list=userService.findAllUsers();
        System.out.println(list);
    }
    @Test
    public void findUserInfoByIdTest(){
        User user=userService.findUserInfoById(1);
        System.out.println(user);
    }

    @Test
    public void addUserInfoTest(){
        User user = new User();
        user.setName("张妈妈");
        user.setAge((short)52);
        user.setAddress("双堰村対窝沟");
        user.setSex((byte)1);
        user.setGrade((byte)3);
        userService.addUserInfo(user);
    }

    @Test
    public void addUserInfosTest(){
        List<User> users=new ArrayList<>();
        User user = new User();
        user.setName("魏爸爸");
        user.setAge((short)57);
        user.setAddress("袁营村庞南祖");
        user.setSex((byte)2);
        user.setGrade((byte)2);
        users.add(user);
        User user2 = new User();
        user2.setName("张爸爸");
        user2.setAge((short)53);
        user2.setAddress("双堰村対窝沟");
        user2.setSex((byte)2);
        user2.setGrade((byte)3);
        users.add(user2);
        userService.addUserInfos(users);
    }
}
