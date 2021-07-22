package com.beitie;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.cglib.proxy.Callback;

public class CommonAdvice implements MethodInterceptor {
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        System.out.println("前置");
        Object proceed = methodInvocation.proceed();
        System.out.println("后置");
        return proceed;
    }
}
