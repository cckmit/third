package com.zb.ws.utils;


import com.zb.ws.service.ProcessListedBizEndInfoImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.List;

public class WrapUtil {

    private final static Log log = LogFactory.getLog(WrapUtil.class);
    private List<HashMap<String, Object>> recordList;


    /**
     *	处理异常返回信息
     * @param param
     * @param code
     * @return
     */
    public String returnMessage(String param, Integer code, String msg_in){
        String success = "false";
        String msg = "";
        if(code == ProcessListedBizEndInfoImpl.status_成功){
            success = "true";
            msg = "操作成功";
        }else if(code == ProcessListedBizEndInfoImpl.status_未知事项类型){
            msg = "未知事项类型";
        }else if(code == ProcessListedBizEndInfoImpl.status_事项类型为空){
            msg = "事项类型为空";
        }else if(code == ProcessListedBizEndInfoImpl.status_事项参数为空){
            msg = "事项参数为空";
        }else if(code == ProcessListedBizEndInfoImpl.status_未知异常){
            msg = "未知异常，请联系管理人员";
        }else if(code == ProcessListedBizEndInfoImpl.status_签名错误){
            msg = "签名错误";
        }else if(code == ProcessListedBizEndInfoImpl.status_未办理保障性住房变更完全产权业务){
            msg = "未办理保障性住房变更完全产权业务";
        }else{
            msg = "系统异常，请联系管理人员";;
        }
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("Response");
        root.addAttribute("success", success);
        root.addAttribute("msg", msg);
        String outxmlstr = doc.asXML();
        System.out.print("------outxmlstr------:"+outxmlstr);
        return outxmlstr;
    }



}