package com.beitie;

import com.beitie.service.OrderService;
import com.beitie.service.impl.OrderServiceImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/5/20
 */
public class ConsumerStart {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("spring/spring-consumer.xml");
        OrderService orderService=ac.getBean(OrderServiceImpl.class);
        orderService.initData();
        System.in.read();
    }
}
