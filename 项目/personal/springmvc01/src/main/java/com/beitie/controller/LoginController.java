package com.beitie.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/8/27
 */
@RestController
public class LoginController {
    @RequestMapping(value="/login",produces = {"application/json;charset=UTF-8"})
    public String login(){
        return "当前用户还没有登录，请重新登录";
    }
}
