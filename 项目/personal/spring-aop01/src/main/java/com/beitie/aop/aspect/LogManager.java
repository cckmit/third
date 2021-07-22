//package com.beitie.aop.aspect;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.*;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//
//@Aspect
////@Component("logManager")
//public class LogManager {
//    @Pointcut("execution(* com.beitie.service..*(..))")
//    public void commonServicePointcut(){
//
//    }
//    @Pointcut("execution(* com.beitie.service..do*(..))")
//    public void commonServiceDoMethodPointcut(){
//
//    }
//    @Before("commonServicePointcut()")
//    public void prepareWork(){
//        System.out.println("正在进行预习功课。");
//    }
//    @After("commonServicePointcut()")
//    public void reviewWork(){
//        System.out.println("正在复习功课。");
//    }
//
//    @AfterReturning("commonServicePointcut()")
//    public void reviewReturnWork(){
//        System.out.println("正在复习功课。--返回值后");
//    }
//    @Around("commonServiceDoMethodPointcut()")
//    public void aroundWork(ProceedingJoinPoint proceedingJoinPoint){
//        Object [] args=proceedingJoinPoint.getArgs();
//        System.out.println("当前方法的参数是"+ Arrays.asList(args));
//        try {
//            proceedingJoinPoint.proceed();
//
//
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//            System.out.println("捕捉到异常");
//        }finally {
//            System.out.println("当前方法执行完毕！！！");
//        }
//    }
//}
//
