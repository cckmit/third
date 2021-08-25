package com.beitie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/8/22
 */
@SpringBootApplication
@EnableEurekaClient
public class StreamRabbitProducer10001 {
    public static void main(String[] args) {
        SpringApplication.run(StreamRabbitProducer10001.class, args);
    }
}
