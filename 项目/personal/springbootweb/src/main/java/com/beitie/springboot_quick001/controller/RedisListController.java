package com.beitie.springboot_quick001.controller;

import com.beitie.springboot_quick001.service.RedisListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisListController {
    @Autowired
    private RedisListService redisListService;
    @RequestMapping("/listPage")
    public String listPage(int pageNum){
        return redisListService.list_page(pageNum).toString();
    }
}
