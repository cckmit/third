package com.beitie.config;

import com.beitie.dao.VideoPlayerDao;
import com.beitie.dao.impl.VideoPlayerDaoHeavy;
import com.beitie.dao.impl.VideoPlayerDaoNormal;
import com.beitie.service.VideoPlayerService;
import com.beitie.service.impl.VideoPlayerServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ComponentScan(basePackages = "com.beitie")
public class AppConfig {
    @Bean
    public VideoPlayerDao createVideoPlayerDao(){
        System.out.println("创建VideoPlayerDaoNormal对象");
        return new VideoPlayerDaoNormal();
    }

    @Bean
    public VideoPlayerDao createVideoPlayerDaoHeavy(){
        System.out.println("创建VideoPlayerDaoHeavy对象");
        return new VideoPlayerDaoHeavy();
    }

    @Bean
    public VideoPlayerService createVideoPlayerService(@Qualifier("createVideoPlayerDao") VideoPlayerDao videoPlayerDao){
        System.out.println("创建VideoPlayerService对象");
        return new VideoPlayerServiceImpl(videoPlayerDao);
    }
}
