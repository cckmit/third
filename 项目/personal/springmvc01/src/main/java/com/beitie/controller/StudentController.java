package com.beitie.controller;

import com.beitie.bean.Student;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@ControllerAdvice
public class StudentController {
    @RequestMapping("/findAllInfo")
    public String findAllInfo(Model model){
        Student student =new Student("张三","瓦亭镇","男");
        student.setName("张三");
        model.addAttribute("user",student);
        return "index";
    }
    @ExceptionHandler(RuntimeException.class)
    public String exceptionHandler(){
        return "error";
    }
    @RequestMapping("/userException")
    public String userException(){
        return ""+5/0;
    }
}
