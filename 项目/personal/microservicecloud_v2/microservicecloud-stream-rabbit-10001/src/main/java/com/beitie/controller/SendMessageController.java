package com.beitie.controller;

import com.beitie.service.JMessageSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/8/22
 */
@Controller
public class SendMessageController {
    @Resource
    private JMessageSender jMessageSender;

    @RequestMapping("/sendMsgg")
    @ResponseBody
    public String sendMsg(){
        return jMessageSender.send();
    }
}
