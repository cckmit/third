package com.beitie.service.impl;

import com.beitie.dao.VideoPlayerDao;
import com.beitie.service.VideoPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class VideoPlayerServiceImpl implements VideoPlayerService {

    @Autowired
    @Qualifier("videoPlayerDaoNormal")
    private VideoPlayerDao videoPlayerDao;

    public VideoPlayerServiceImpl(VideoPlayerDao videoPlayerDao) {
        this.videoPlayerDao = videoPlayerDao;
    }

    public VideoPlayerServiceImpl(){

    }
    @Override
    public void play() {
        videoPlayerDao.play();
    }
}
