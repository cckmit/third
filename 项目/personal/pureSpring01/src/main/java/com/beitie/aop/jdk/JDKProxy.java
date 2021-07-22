package com.beitie.aop.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKProxy implements InvocationHandler {
    private Object objectTarget;
    public Object createInstanceProxy(Object objectTarget){
        this.objectTarget =objectTarget;
        return Proxy.newProxyInstance(objectTarget.getClass().getClassLoader(),objectTarget.getClass().getInterfaces(),this);
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object o = null;
        System.out.println("前值调用");
        o = method.invoke(objectTarget,args);
        System.out.println("后置调用");
        return o;
    }
}
