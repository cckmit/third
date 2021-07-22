package com.beitie.publisher;


import com.beitie.webserviceBeiTie.UserInfoService;
import com.beitie.webserviceBeiTie.impl.UseInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.ws.Endpoint;
@Controller
@RequestMapping("/webservice")
public class UserInfoServicePublisher {

    private final UserInfoService wsUseInfoService;
    @Autowired
    public UserInfoServicePublisher(UserInfoService wsUseInfoService) {
        this.wsUseInfoService = wsUseInfoService;
    }

    @RequestMapping("/publishUserInfoService")
    @ResponseBody
    public String publishUserInfoService(){
        Endpoint.publish("http://127.0.0.1:12345/userInfo",wsUseInfoService);
        System.out.println("发布成功");
        String msg="webservice publish success!";
        return msg;
    }

    public static void main(String[] args) {
        Endpoint.publish("http://127.0.0.1:12345/userInfo",new UseInfoServiceImpl());
        System.out.println("发布成功");
    }
}
