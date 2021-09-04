package com.beitie.proxy.jdkproxy;

import com.beitie.service.IUserService;
import com.beitie.service.impl.UserServiceImpl;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/8/31
 */
public class JDKProxyTest {
    public static void main(String[] args) {
        IUserService iUserService = new UserServiceImpl();
        IUserService  iUserServiceProxy=(IUserService)JDKProxyUtils.createProxy(iUserService);
        iUserServiceProxy.study();
    }
}
