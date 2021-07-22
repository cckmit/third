package com.beitie.handler;

import com.beitie.bean.User;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class UserInfoHandler implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<User> list=new ArrayList<User>();
        User user=new User();
        user.setUname("张三");
        user.setUage(21);
        user.setUid(1);
        User user2=new User();
        user2.setUname("李四");
        user2.setUage(22);
        user2.setUid(2);
        list.add(user);
        list.add(user2);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("userInfoList",list);
        modelAndView.setViewName("userInfo");
        return modelAndView;
    }
}
