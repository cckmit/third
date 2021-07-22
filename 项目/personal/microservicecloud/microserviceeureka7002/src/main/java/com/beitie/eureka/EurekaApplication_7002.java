package com.beitie.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication_7002 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication_7002.class,args);
    }
}
