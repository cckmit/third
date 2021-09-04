package com.beitie.proxy.jdkproxy;

import java.lang.reflect.Proxy;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/8/31
 */
public class JDKProxyUtils {
    public static Object createProxy(Object o){
        Object oProxy= Proxy.newProxyInstance(o.getClass().getClassLoader(),o.getClass().getInterfaces(),new JDkProxyHandler(o));
        return oProxy;
    }
}
