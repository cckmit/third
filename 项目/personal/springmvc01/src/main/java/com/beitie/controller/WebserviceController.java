package com.beitie.controller;

import com.beitie.bean.Student;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/11/2
 */
@RestController
public class WebserviceController {
    @PostMapping("/msg")
    public void getMsg( Student student,String userName,String password,HttpServletRequest request){
        String userN =  request.getAttributeNames().toString();
        System.out.println("msg");
        request.getHeaderNames();
        request.getHeader("");
    }
}
