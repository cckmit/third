package com.beitie.lygdp_normal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/11/13
 */
@Controller
public class TestController {
    @RequestMapping("/hello")
    @ResponseBody
    public String helloWorld(){
        String msg = "hello world!";
        return msg;
    }
}
