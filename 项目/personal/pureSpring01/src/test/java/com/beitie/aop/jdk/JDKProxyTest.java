package com.beitie.aop.jdk;

import com.beitie.service.StudentService;
import com.beitie.service.impl.StudentServiceImpl;
import org.junit.Test;

/**
 * jdk动态代理满足条件（不需要引入额外的jar包，由jdk自身运行环境就可以）
 * 1.被代理的对象必须是类，而且此类必须实现接口
 * 2.代理类是由  {@link java.lang.reflect.Proxy newProxyInstance()}生成的
 * 3.代理时产生代理类并对代理类进行增强的类必须实现  {@link java.lang.reflect.InvocationHandler}接口，实现其invoke方法
 */


public class JDKProxyTest {
    @Test
    public void studentStudyTest(){
        JDKProxy jdkProxy =new JDKProxy();
        StudentService studentService= (StudentService)jdkProxy.createInstanceProxy(new StudentServiceImpl());
        studentService.study();
    }
}
