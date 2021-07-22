package com.beitie.util;

import com.beitie.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class BeanFactory {
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionUtil transactionUtil;
    public AccountService createAccountServiceProxy(){
        return (AccountService)Enhancer.create(AccountService.class, new MethodInterceptor() {
            public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                Object returnValule = null;
                transactionUtil.beginTransaction();
                try {
                    returnValule = method.invoke(accountService,args);
                    transactionUtil.commitTransaction();
                }catch (Exception e){
                    e.printStackTrace();
                    transactionUtil.rollbackTransaction();
                }finally {
                    transactionUtil.releaseTransaction();
                }

                return returnValule;
            }
        });
    }
}
