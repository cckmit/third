package com.beitie.controller;

import com.beitie.base.bean.User;
import com.beitie.base.service.UserService;
import com.beitie.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/5/20
 */
@Controller
public class OrderController {
    @Autowired
    OrderService orderService;
    @ResponseBody
    @RequestMapping("/init")
    public List<User> initData(){
        return orderService.initData();
    }
}
