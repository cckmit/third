package com.zb.ws.service;

import com.zb.bean.SsjyxxLog;
import com.zb.service.SsjyxxLogService;
import com.zb.service.SsjyxxService;
import com.zb.ws.po.fetchBizInfo.FetchBizInfoRequest;
import com.zb.ws.po.fetchBizInfo.FetchBizInfoResponse;
import com.zb.ws.po.fetchBizInfo.Jyxx;
import com.zb.ws.utils.EncryptTools;
import com.zb.ws.utils.WrapUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.transport.http.AbstractHTTPDestination;

import javax.servlet.http.HttpServletRequest;
import java.security.GeneralSecurityException;
import java.util.*;

import static com.zb.ws.utils.RSAUtil.getSign;
import static com.zb.ws.utils.RSAUtil.validateSign;
import static com.zb.ws.utils.SignHolder.setSign;
import static com.zb.ws.utils.SignHolder.takeSign;
import static com.zb.ws.utils.XmlUtil.convertToXml;
import static com.zb.ws.utils.XmlUtil.convertXmlStrToObject;

public class ProcessListedBizEndInfoImpl implements ProcessListedBizEndInfo{
//    private Logger log = LoggerFactory.getLogger(ProcessListedBizEndInfoImpl.class);
    private SsjyxxService ssjyxxService;
    private SsjyxxLogService ssjyxxLogService;

    WrapUtil wrapUtil = new WrapUtil();
    public static final  String SQL = "select o.ywslid as ywslid ,o.sqrmc as sqrmc,o.sqrzjhm as sqrzjhm,o.zjnje as zjnje,o.fwzl as fwzl,o.fwjzmj as fwjzmj  from lygzb_zfbz.ssjyxx o  ";
    private static final String TYPE_产权人姓名 = "1001";
    private static final String TYPE_产权人身份证号码 = "1002";
    private static final String TYPE_上市交易业务编号 = "1003";
    public static final Integer status_成功 = 0;
    public static final Integer status_未知事项类型 = 1;
    public static final Integer status_事项类型为空 = 2;
    public static final Integer status_事项参数为空 = 3;
    public static final Integer status_签名错误 = 4;
    public static final Integer status_未知异常 = 5;
    public static final Integer status_未办理保障性住房变更完全产权业务 = 6;
    private String clientIP;
    @Override
    public String fetchListedBizEndInfo(String inxmlstr) {
        String outputStr = "";
        String clientPara= inxmlstr;
        String clientSign =takeSign();
        String msg = "";
        Integer statusCurr=null;
        String decodeData =  EncryptTools.SM4Decode(inxmlstr);
        FetchBizInfoRequest objectRequest = (FetchBizInfoRequest)convertXmlStrToObject(FetchBizInfoRequest.class, decodeData);
        try {
            UUID.randomUUID().toString();
            if(validateSign(objectRequest,takeSign())){

                Map<String,Object> mapPara = checkAndPackageParameter(objectRequest);
                Integer statusCode = (Integer)mapPara.get("statusCode");
                statusCurr =statusCode;
                if(status_成功.equals(statusCode)){
                    Map<String,Object> mapData =listToFetchBizInfoResponse(ssjyxxService.fetchListedBizInfo(mapPara));
                    statusCode = (Integer)mapData.get("statusCode");
                    if(status_成功.equals(statusCode)){
                        FetchBizInfoResponse response = (FetchBizInfoResponse)mapData.get("data");
                        outputStr = convertToXml(response);
                        statusCurr =statusCode;
//                        return dealResult(outputStr);
                    }else{
                        statusCurr =statusCode;
//                        return dealResult(wrapUtil.returnMessage(null, statusCurr, msg));
                    }
                }else{
                    statusCurr =statusCode;
//                    return dealResult(wrapUtil.returnMessage(null, statusCurr, msg));
                }
            }else{
                msg = "签名校验失败！";
                statusCurr = status_签名错误;
//                return dealResult(wrapUtil.returnMessage(null, statusCurr, msg));
            }
        } catch (Exception e) {
            e.printStackTrace();
            statusCurr =status_未知异常;
//            return dealResult(wrapUtil.returnMessage(null, statusCurr, msg));
        }finally {
            try{
                logRecord(clientPara,clientSign,statusCurr);
            }catch (Exception e){
                e.printStackTrace();
//                statusCurr =status_未知异常;
            }
            if(StringUtils.isBlank(outputStr) && statusCurr != status_成功){
                outputStr = wrapUtil.returnMessage(null, statusCurr, msg);
            }
            System.out.println("输出内容为=========================="+outputStr);
            return dealResult(outputStr);
        }
    }

    private Map<String,Object> listToFetchBizInfoResponse(List<Jyxx> list){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("statusCode",status_成功);
        FetchBizInfoResponse response = new FetchBizInfoResponse();
        if(list.size() > 0){
            List<Jyxx> jyxxes = new ArrayList<Jyxx>();
            response.setJyxx(list);
            response.setMsg("数据获取成功");
            response.setSuccess("true");
            map.put("data",response);
        }else{
            map.put("statusCode",status_未办理保障性住房变更完全产权业务);
        }
        return map;
    }

    private Map<String,Object> checkAndPackageParameter(FetchBizInfoRequest request){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("statusCode",status_成功);
        String sxlx = request.getSxlx();
        String sxcs = request.getSjcs();
        StringBuffer sqlBuffer = new StringBuffer(SQL);
        if(StringUtils.isNotBlank(sxlx)){
            if(StringUtils.isBlank(sxcs)){
                map.put("statusCode",status_事项参数为空);
            }
            if(TYPE_产权人姓名.equals(sxlx)){
                map.put("sqrmc",sxcs);
            }else if(TYPE_产权人身份证号码.equals(sxlx)){
                map.put("sqrzjhm",sxcs);
            }else if(TYPE_上市交易业务编号.equals(sxlx)){
                map.put("ywslid",sxcs);
            }else{
                map.put("statusCode",status_未知事项类型);
            }
        }else{
            map.put("statusCode",status_事项类型为空);
        }
        return map;
    }
    private Map<String,Object> packageSql(FetchBizInfoRequest request){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("statusCode",status_成功);
        String sxlx = request.getSxlx();
        String sxcs = request.getSjcs();
        StringBuffer sqlBuffer = new StringBuffer(SQL);
        if(StringUtils.isNotBlank(sxlx)){
            if(StringUtils.isBlank(sxcs)){
                map.put("statusCode",status_事项参数为空);
            }
            if(TYPE_产权人姓名.equals(sxlx)){
                sqlBuffer.append("  where o.sqrmc = '" + sxcs +"'");
            }else if(TYPE_产权人身份证号码.equals(sxlx)){
                sqlBuffer.append("  where o.sqrzjhm = '" + sxcs +"'");
            }else if(TYPE_上市交易业务编号.equals(sxlx)){
                sqlBuffer.append("  where o.ywslid = '" + sxcs +"'");
            }else{
                map.put("statusCode",status_未知事项类型);
            }
        }else{
            map.put("statusCode",status_事项类型为空);
        }
        map.put("sql",sqlBuffer.toString());
        return map;
    }

    private String dealResult(String result){
        //进行日志记录

        FetchBizInfoResponse objectResponse = (FetchBizInfoResponse) convertXmlStrToObject(FetchBizInfoResponse.class,result);
        try {
            setSign(getSign(objectResponse));
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return EncryptTools.SM4Encode(result);
    }
    private void logRecord(String clientPara,String clientSign,int statusCurr){

        Message message = PhaseInterceptorChain.getCurrentMessage();
        HttpServletRequest request = (HttpServletRequest)message.get(AbstractHTTPDestination.HTTP_REQUEST);
        //获取请求IP
        clientIP = request.getRemoteAddr();
        Date currDate = new Date();
        SsjyxxLog ssjyxxLog = new SsjyxxLog();
        ssjyxxLog.setId(UUID.randomUUID().toString());
        ssjyxxLog.setClientip(clientIP);
        ssjyxxLog.setClientPara(clientPara);
        ssjyxxLog.setClientReqTime(currDate);
        ssjyxxLog.setClientSign(clientSign);
        ssjyxxLog.setResultCode(statusCurr+"");
        ssjyxxLogService.insertData(ssjyxxLog);
    }

    public SsjyxxService getSsjyxxService() {
        return ssjyxxService;
    }

    public void setSsjyxxService(SsjyxxService ssjyxxService) {
        this.ssjyxxService = ssjyxxService;
    }

    public SsjyxxLogService getSsjyxxLogService() {
        return ssjyxxLogService;
    }

    public void setSsjyxxLogService(SsjyxxLogService ssjyxxLogService) {
        this.ssjyxxLogService = ssjyxxLogService;
    }
}
