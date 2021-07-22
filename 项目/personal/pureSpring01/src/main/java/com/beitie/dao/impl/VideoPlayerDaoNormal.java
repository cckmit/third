package com.beitie.dao.impl;

import com.beitie.dao.VideoPlayerDao;
import com.beitie.service.VideoPlayerService;
import org.springframework.context.annotation.Bean;

public class VideoPlayerDaoNormal implements VideoPlayerDao {
    @Override
    public void play() {
        System.out.println("正在播放音乐。。。。。。");
    }
}
