package com.beitie.webService;

import com.beitie.util.EncryptTools;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/12/1
 */
public class FetchListedInfoServiceImpl implements FetchListedInfoService{
    @Override
    @WebResult(name = "outputStr")
    public String fetchInfo(String text) {
        System.out.println(text);
        String decodeData =  EncryptTools.SM4Decode(text);
        return text;
    }
}
