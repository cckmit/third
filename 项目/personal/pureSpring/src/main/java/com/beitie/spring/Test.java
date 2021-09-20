package com.beitie.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    public static void main(String[] args) {
        System.out.println("----容器启动----");
        ClassPathXmlApplicationContext xml =new ClassPathXmlApplicationContext("spring-lifecycle-test.xml");
        System.out.println("----启动完成----");
        Person person=xml.getBean("person",Person.class);
        System.out.println("获取延迟加载对象student");
        Student student=xml.getBean("student",Student.class);
        System.out.println("获取加载对象studentPrototype");
        Student studentPrototype=xml.getBean("studentPrototype",Student.class);
        System.out.println("---准备关闭spring容器-----");
        xml.registerShutdownHook();
    }
}
