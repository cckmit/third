package com.beitie.dao.impl;

import com.beitie.dao.VideoPlayerDao;

public class VideoPlayerDaoHeavy implements VideoPlayerDao {
    @Override
    public void play() {
        System.out.println("正在播放重音乐。。。。。。");
    }
}
