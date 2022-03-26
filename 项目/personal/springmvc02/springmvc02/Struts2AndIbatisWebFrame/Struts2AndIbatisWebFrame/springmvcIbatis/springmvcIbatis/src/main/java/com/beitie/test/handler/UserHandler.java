package com.beitie.test.handler;

import com.beitie.test.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/10/22
 */
@Controller
@RequestMapping("/user")
public class UserHandler {
    @Autowired
    UserDao userDao;
    @RequestMapping("/getUserInfo")
    @ResponseBody
    public HashMap getUserInfo(){
        HashMap hashMap=userDao.getUserInfoById("query","1");
        return hashMap;
    }
}
