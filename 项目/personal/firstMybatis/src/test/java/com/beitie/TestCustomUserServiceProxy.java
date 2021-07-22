package com.beitie;

import com.beitie.test.CustomUserService;
import com.beitie.test.CustomUserServiceProxy;
import org.junit.Test;

public class TestCustomUserServiceProxy {
    @Test
    public void testProxy(){

        CustomUserService customUserService=new CustomUserService();
        CustomUserServiceProxy proxy=new CustomUserServiceProxy(customUserService);
        customUserService=proxy.createProxy();
        customUserService.delete();
        customUserService.save();
        customUserService.query();
        customUserService.update();
    }
}
