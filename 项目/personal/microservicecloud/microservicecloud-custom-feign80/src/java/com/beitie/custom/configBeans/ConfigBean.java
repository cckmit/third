package com.beitie.custom.configBeans;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConfigBean {
    @Bean
    @LoadBalanced // ribbon基于netfix的一种客户端开发工具
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
//    @Bean
////    public IRule getIRule(){
////        return new RandomRule();
////    }
}
