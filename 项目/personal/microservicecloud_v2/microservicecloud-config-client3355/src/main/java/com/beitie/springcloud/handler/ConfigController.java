package com.beitie.springcloud.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class ConfigController {
    @Value("${spring.application.name}")
    private String applicationName;
    @Value("${eureka.client.service-url.defaultZone}")
    private String eurekaDefaultZone;
    @Value("${server.port}")
    private String port;
    @Value("${config.info}")
    private String configInfo;
    @RequestMapping("/config")
    public String getConfig(){
        return "配置信息："+configInfo+"  \r注册中心1url："
                +eurekaDefaultZone+"    端口号："+port
                +"\r服务名" + applicationName+"";
    }
}
