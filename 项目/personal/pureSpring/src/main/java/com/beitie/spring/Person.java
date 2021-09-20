package com.beitie.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;

public class Person implements BeanFactoryAware, BeanNameAware, InitializingBean, DisposableBean {


    {
        System.out.println("加载person的动态代码块");
    }
    static {
        System.out.println("加载person的静态代码块");
    }
    static String getMsg(){
        System.out.println("静态变量获取信息msg");
        return "msg";
    }
    private String name;
    private int age;
    private String phone;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Person() {
        super();
        System.out.println("调用Person的构造器");
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("调用Person的setBeanFactory方法");
    }

    public void setBeanName(String name) {
        System.out.println("调用Person的setBeanName方法");
    }

    public void destroy() throws Exception {
        System.out.println("调用Person的destroy方法");
    }

    public void afterPropertiesSet() throws Exception {
        System.out.println("调用Person的afterPropertiesSet方法");
    }

    public void myInit(){
        System.out.println("调用Person的myInit方法");
    }
    public void myDestroy(){
        System.out.println("调用Person的myDestroy方法");
    }
}
