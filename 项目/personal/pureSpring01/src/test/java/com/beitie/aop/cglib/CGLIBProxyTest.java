package com.beitie.aop.cglib;

import com.beitie.service.StudentService;
import com.beitie.service.impl.StudentServiceImpl;
import net.sf.cglib.proxy.Enhancer;
import org.junit.Test;

public class CGLIBProxyTest {
    @Test
    public void proxyTest(){
        CGLIBProxy cglibProxy = new CGLIBProxy();
        Enhancer enhancer = new Enhancer();
        StudentService studentService=(StudentService)enhancer.create(StudentServiceImpl.class,StudentServiceImpl.class.getInterfaces(),cglibProxy);
//        enhancer.setCallback(cglibProxy);
//        enhancer.setSuperclass(StudentServiceImpl.class);
//        StudentService studentService=(StudentService)enhancer.create();
        studentService.study();
    }
}
