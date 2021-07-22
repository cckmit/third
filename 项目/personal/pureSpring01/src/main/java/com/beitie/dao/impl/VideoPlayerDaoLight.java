package com.beitie.dao.impl;

import com.beitie.dao.VideoPlayerDao;

public class VideoPlayerDaoLight implements VideoPlayerDao {
    @Override
    public void play() {
        System.out.println("正在播放轻音乐。。。。。。");
    }
}
