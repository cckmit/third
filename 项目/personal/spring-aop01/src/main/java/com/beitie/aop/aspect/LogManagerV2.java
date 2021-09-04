package com.beitie.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;


//@Aspect
@Component("logManagerV2")
public class LogManagerV2 {
    public void commonServicePointcut(){

    }
    public void commonServiceDoMethodPointcut(){
        System.out.println();
    }
    public void prepareWork(){
        System.out.println("正在进行预习功课。");
    }
    public void reviewWork(){
        System.out.println("正在复习功课。");
    }

    public void reviewReturnWork(){
        System.out.println("正在复习功课。--返回值后");
    }

    public void aroundWork(ProceedingJoinPoint proceedingJoinPoint){
        Object [] args=proceedingJoinPoint.getArgs();
        System.out.println("当前方法的参数是"+ Arrays.asList(args));
        try {
            proceedingJoinPoint.proceed(args);


        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.out.println("捕捉到异常");
        }finally {
            System.out.println("当前方法执行完毕！！！");
        }
    }
}

