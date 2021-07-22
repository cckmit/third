package com.beitie.service;

import com.beitie.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class StudentServiceImplTest {
    @Autowired
    private IStudentService iStudentService;
    @Test
    public void studyTest(){
        iStudentService.study();
    }

    @Test
    public void playTest(){
        iStudentService.doPlay("篮球",10);
    }
}
