package com.beitie.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class Dept_Provider_8080 {
    public static void main(String[] args) {
        SpringApplication.run(Dept_Provider_8080.class,args);
    }
}
