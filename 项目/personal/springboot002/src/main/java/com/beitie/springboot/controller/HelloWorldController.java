package com.beitie.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @RequestMapping("/getMsg")
    public String helloWorld(){
        return "Hello World000000";
    }
}
