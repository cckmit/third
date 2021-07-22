package com.beitie.springboot_quick001;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;

@SpringBootApplication
@ImportResource(locations = {"classpath:spring/spring-extra.xml"})
public class SpringbootQuick001Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootQuick001Application.class, args);
    }

}
