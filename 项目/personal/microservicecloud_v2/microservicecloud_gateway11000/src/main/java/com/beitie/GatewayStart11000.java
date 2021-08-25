package com.beitie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/8/25
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class GatewayStart11000 {
    public static void main(String[] args) {
        SpringApplication.run(GatewayStart11000.class,args);
    }
}
