package com.beitie.aop.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CGLIBProxy implements MethodInterceptor{
    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Object obj = null;
        System.out.println("cglib的前置通知");
        obj= methodProxy.invokeSuper(o,args);
        System.out.println("cglib的后置通知");
        return obj;
    }
}
