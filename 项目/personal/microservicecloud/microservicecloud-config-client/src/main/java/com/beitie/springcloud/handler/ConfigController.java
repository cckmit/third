package com.beitie.springcloud.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {
    @Value("${spring.application.name}")
    private String applicationName;
    @Value("${eureka.client.service-url.defaultZone}")
    private String eurekaDefaultZone;
    @Value("${server.port}")
    private String port="8002";
    @RequestMapping("/config")
    public String getConfig(){
        return "服务名："+applicationName+"  注册中心url："+eurekaDefaultZone+"    端口号："+port;
    }
}
