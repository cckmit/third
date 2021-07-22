package com.beitie.service.impl;

import com.beitie.dao.UserInfoMapper;
import com.beitie.pojo.UserInfo;
import com.beitie.service.UserInfoService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebService;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

@WebService
public class UseInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Override
    public UserInfo findUserInfoById(int id) {
        return userInfoMapper.findUserInfoById(id);
    }

    @Override
    public List<UserInfo> findAllUsers() {
        return userInfoMapper.findAllUsers();
    }
}
