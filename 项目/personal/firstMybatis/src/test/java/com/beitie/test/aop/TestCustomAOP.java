package com.beitie.test.aop;

import com.beitie.test.aop.sevice.UserAOPService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/springApplitionContext-aop.xml")
public class TestCustomAOP {
    @Autowired
    private UserAOPService userAOPService;
    @Test
    public void testAdd(){
        userAOPService.add();
    }
    @Test
    public  void testDelete(){
        userAOPService.delete();
    }
    @Test
    public  void testUpdate(){
        userAOPService.update();
    }
    @Test
    public  void testFind(){
        userAOPService.find();
    }
}
