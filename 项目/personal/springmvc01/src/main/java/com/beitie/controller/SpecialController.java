package com.beitie.controller;

import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/8/27
 */
@RestController
public class SpecialController {
    @RequestMapping("/connect")
    public String httpConnectRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        InputStream inputStream = request.getInputStream();
        int length = 0;
        int lengthTotal = request.getContentLength();
        byte [] bytes = new byte[lengthTotal];
        while(true){
            inputStream.read(bytes,length,inputStream.available());
            length = inputStream.available();
            if(length == 0){
                break;
            }
        }
        System.out.println(new String(bytes));
        return "no you are not";
    }

    @RequestMapping(value="/removeSession",produces = {"application/json;charset=UTF-8"})
    public String removeSession(HttpSession session){
        session.removeAttribute("student");
        return "sesion 已移除";
    }
    @RequestMapping("/specialError")
    public String specialError(){
        return ""+3/0;
    }

    @RequestMapping("/getJsonString")
    public Map getJsonString(){
        Map map = new HashMap();
        map.put("name","张三");
        map.put("address","瓦亭");
        map.put("grade","高一");

        return map;
    }
}
