package com.beitie.webserviceBeiTie.impl;

import com.beitie.dao.UserInfoMapper;
import com.beitie.pojo.UserInfo;
import com.beitie.webserviceBeiTie.UserInfoService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebService;
import java.io.IOException;
import java.io.Reader;

@WebService
public class UseInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;
   /* @Override
    public UserInfo findUserInfoById(int id) {
        System.out.println("有人访问了我");
        return userInfoMapper.findUserInfoById(id);
    }*/

    @Override
    public String getMsg() {
        return "success";
    }
}
