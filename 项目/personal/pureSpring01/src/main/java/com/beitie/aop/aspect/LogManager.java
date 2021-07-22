package com.beitie.aop.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class LogManager {
    @Before("")
    public void beforeProcess(){
        System.out.println("预先处理方法执行");
    }
    public void afterProcess(){
        System.out.println("后处理方法执行");
    }
}
