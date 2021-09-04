package com.beitie.proxy.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/8/31
 */
public class JDkProxyHandler implements InvocationHandler {
    private Object oModel;

    public JDkProxyHandler(Object oModel) {
        this.oModel = oModel;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("前置处理程序");
        Object o = method.invoke(oModel,args);
        System.out.println("后置处理程序");
        return o;
    }


}
