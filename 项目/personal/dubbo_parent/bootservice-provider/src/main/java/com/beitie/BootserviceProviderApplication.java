package com.beitie;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@EnableDubbo
@SpringBootApplication
public class BootserviceProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootserviceProviderApplication.class, args);
    }

}
