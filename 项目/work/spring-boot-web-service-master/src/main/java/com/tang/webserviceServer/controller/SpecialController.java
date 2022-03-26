package com.tang.webserviceServer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/special")
public class SpecialController {
    @RequestMapping("/getMsg")
    public void getMsg(){
        String msg  = "中国人";
    }
}
