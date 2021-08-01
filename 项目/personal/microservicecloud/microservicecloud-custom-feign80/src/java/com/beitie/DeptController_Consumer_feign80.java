package com.beitie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
//@EnableEurekaClient
@EnableFeignClients
@EnableHystrix
public class DeptController_Consumer_feign80 {
    public static void main(String[] args) {
        SpringApplication.run(DeptController_Consumer_feign80.class,args);
    }
}
