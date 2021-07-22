package com.beitie.handler;

import com.beitie.bean.User;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserInfoHandler2 implements HttpRequestHandler {
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> list=new ArrayList<>();
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
        request.setAttribute("userInfoList",list);
        request.getRequestDispatcher("/WEB-INF/jsp/userInfo.jsp").forward(request,response);
    }
}
