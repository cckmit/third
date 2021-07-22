package com.beitie.service;

import com.beitie.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AppConfig.class)
@ContextConfiguration("classpath:spring-simpleConfig.xml")
public class VideoPlayerServiceTest {
    @Autowired
    @Qualifier("videoPlayerServiceLight")
    private VideoPlayerService videoPlayerService;
    @Test
    public void playTest(){
        videoPlayerService.play();
    }


}
