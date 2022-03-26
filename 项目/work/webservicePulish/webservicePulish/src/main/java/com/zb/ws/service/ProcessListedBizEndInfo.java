package com.zb.ws.service;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
* @Author：Weitj
* @Description：获取已上市已办结的交易信息
* @Date：13:56 2021/12/1 0001
* @Return
*/
@WebService
public interface ProcessListedBizEndInfo {
    /**
    * @Author：Weitj
    * @Description：
    * @Date：14:00 2021/12/1 0001
    * @Param 输入参数
    * @Return 
    */
    @WebResult(name="outxmlstr")
    String fetchListedBizEndInfo(@WebParam(name = "inxmlstr") String inxmlstr);
}
