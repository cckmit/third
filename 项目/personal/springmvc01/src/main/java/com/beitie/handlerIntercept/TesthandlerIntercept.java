package com.beitie.handlerIntercept;

import com.beitie.bean.Student;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/8/27
 */
public class TesthandlerIntercept implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Student student = (Student)session.getAttribute("student");
        if (student == null){
            //还没登录，重定向到登录页面
            response.sendRedirect("/login.action");
            // request.getRequestDispatcher("/login.action").forward(request,response);
//            Student studentNew= new Student(UUID.randomUUID().toString(),null,null);
            Student studentNew= new Student();
            session.setAttribute("student",studentNew);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
