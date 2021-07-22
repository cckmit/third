package com.beitie.test.aop.aspect;

import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public class CustomAspectOne {
    public void checkAuthority(JoinPoint joinPoint){
        System.out.println("正在进行权限验证");
        System.out.println("joinPoint==="+joinPoint);
    }
    public void WriteLog(Object result){
        System.out.println("正在进行日志记录");
        System.out.println(result);
    }
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("这是前置通知");
        Object proceed = joinPoint.proceed();
        System.out.println("这是后置通知");
        return proceed;
    }
    public void throwException(Throwable exception){
        System.out.println("本方法抛出异常了");
        System.out.println("异常信息为：：：：："+exception.getMessage());
    }
}
