package com.beitie.test;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CustomUserServiceProxy implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if(method.getName().startsWith("save")){
            System.out.println("我被切入");
        }else if(method.getName().startsWith("delete")){
            System.out.println("我被删除了");
        }
        return  methodProxy.invoke(this.customUserService,objects);
    }
    private  CustomUserService customUserService;

    public CustomUserService getCustomUserService() {
        return customUserService;
    }

    public void setCustomUserService(CustomUserService customUserService) {
        this.customUserService = customUserService;
    }

    public CustomUserServiceProxy(CustomUserService customUserService){
        this.customUserService=customUserService;
    }

    public CustomUserService createProxy(){
        Enhancer enhancer=new Enhancer();
        enhancer.setSuperclass(CustomUserService.class);
        enhancer.setCallback(this);
        CustomUserService customUserService=(CustomUserService)enhancer.create();
        return customUserService;
    }
}
