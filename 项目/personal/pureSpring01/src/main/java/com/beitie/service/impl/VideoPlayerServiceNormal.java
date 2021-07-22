package com.beitie.service.impl;

import com.beitie.dao.VideoPlayerDao;
import com.beitie.service.VideoPlayerService;
import org.springframework.stereotype.Service;

public class VideoPlayerServiceNormal implements VideoPlayerService {
    private VideoPlayerDao videoPlayerDao;

    public VideoPlayerServiceNormal(VideoPlayerDao videoPlayerDao) {
        this.videoPlayerDao = videoPlayerDao;
    }

    public VideoPlayerServiceNormal(){

    }
    @Override
    public void play() {
        videoPlayerDao.play();
    }
}
