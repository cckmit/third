package com.beitie.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/8/27
 */
@RestController
public class SpecialController {
    @RequestMapping(value="/removeSession",produces = {"application/json;charset=UTF-8"})
    public String removeSession(HttpSession session){
        session.removeAttribute("student");
        return "sesion 已移除";
    }
    @RequestMapping("/specialError")
    public String specialError(){
        return ""+3/0;
    }
}
