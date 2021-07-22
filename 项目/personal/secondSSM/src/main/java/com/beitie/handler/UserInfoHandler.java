package com.beitie.handler;

import com.beitie.pojo.UserInfo;
import com.beitie.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/userInfo")
public class UserInfoHandler {
    private final UserInfoService userInfoService;
    @Autowired
    public UserInfoHandler(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @RequestMapping("/findAllUsers")
    public String findAllUsers(Model model){
        List<UserInfo> list=userInfoService.findAllUsers();
        model.addAttribute("userInfoList",list);
        return "userInfo";
    }

    @RequestMapping("/querySingleUserInfoForJson")
    @ResponseBody
    public UserInfo querySingleUserInfoForJson(Integer uid){
        UserInfo userInfo=userInfoService.findUserInfoById(uid);
        return userInfo;
    }
}
