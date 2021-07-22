package com.beitie.springboot_quick001.service;

import com.beitie.springboot_quick001.SpringbootQuick001Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootQuick001Application.class)
public class UserServiceTest {
    @Resource
    UserService userService;
    @Test
    public void getUserLockTimeTest(){
        userService.getUserLockTime("zhangsan");
    }
}
