package com.beitie;

import com.beitie.webservicebeitie.impl.UseInfoServiceImpl;
import com.beitie.webservicebeitie.impl.UseInfoServiceImplService;
import com.beitie.webservicebeitie.impl.UserInfo;
import org.junit.Test;

public class TestUserWebService {
    @Test
    public void testWebservice(){
        UseInfoServiceImplService useInfoServiceImplService=new UseInfoServiceImplService();
        UseInfoServiceImpl useInfoService=useInfoServiceImplService.getPort(UseInfoServiceImpl.class);
        String msg = useInfoService.getMsg();
        System.out.println(msg);
    }
}
