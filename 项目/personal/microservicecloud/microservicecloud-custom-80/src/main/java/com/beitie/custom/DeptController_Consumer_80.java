package com.beitie.custom;

import com.beitie.ribbonrule.MyRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@SpringBootApplication
@EnableEurekaClient
@RibbonClient(name = "MICROSERVICECLOUD-DEPT-PROVIDER",configuration = RoundRobinRule.class)
public class DeptController_Consumer_80 {
    public static void main(String[] args) {
        SpringApplication.run(DeptController_Consumer_80.class,args);
    }
}
