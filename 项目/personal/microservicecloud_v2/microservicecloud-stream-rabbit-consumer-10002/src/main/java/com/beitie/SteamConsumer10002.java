package com.beitie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/8/23
 */
@SpringBootApplication
@EnableEurekaClient
public class SteamConsumer10002 {
    public static void main(String[] args) {
        SpringApplication.run(SteamConsumer10002.class,args);
    }
}
