package com.beitie.springboot_quick001.service;

import com.beitie.springboot_quick001.SpringbootQuick001Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootQuick001Application.class)
public class TestServiceTest {
    @Autowired
    TestService testService;
    @Test
    public void test(){
        testService.test();
    }
}
