package com.tang.webserviceServer.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import sun.misc.BASE64Encoder;

import javax.xml.namespace.QName;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommonUtils {


    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
    static SimpleDateFormat sdf_2 = new SimpleDateFormat("yyyy-MM-dd");


    public static JSONArray listToTree(JSONArray arr, String id, String pid, String child) {
        JSONArray result = new JSONArray();
        JSONObject hash = new JSONObject();
        //将数组转为Object的形式，key为数组中的id
        for (int i = 0; i < arr.size(); i++) {
            JSONObject json = (JSONObject) arr.get(i);
            hash.put(json.getString(id), json);
        }
        //遍历结果集
        for (int j = 0; j < arr.size(); j++) {
            //单条记录
            JSONObject aVal = (JSONObject) arr.get(j);
            //在hash中取出key为单条记录中pid的值
            JSONObject hashVP = (JSONObject) hash.get(aVal.get(pid).toString());
            //如果记录的pid存在，则说明它有父节点，将她添加到孩子节点的集合中
            if (hashVP != null) {
                //检查是否有child属性
                if (hashVP.get(child) != null) {
                    JSONArray ch = (JSONArray) hashVP.get(child);
                    ch.add(aVal);
                    hashVP.put(child, ch);
                } else {
                    JSONArray ch = new JSONArray();
                    ch.add(aVal);
                    hashVP.put(child, ch);
                }
            } else {
                result.add(aVal);
            }
        }
        for (Object obj : result) {
            JSONObject item = (JSONObject)obj;
            item.put("level", 1);
            if (item.containsKey("children")) {
                JSONArray children = item.getJSONArray("children");
                for (Object obj2 : children) {
                    JSONObject son = (JSONObject)obj2;
                    son.put("level", 2);

                    if (son.containsKey("children")) {
                        JSONArray grandChildren = son.getJSONArray("children");
                        for (Object obj3 : grandChildren) {
                            JSONObject grandSon = (JSONObject)obj3;
                            grandSon.put("level", 3);
                        }
                    }
                }
            }

        }
        return result;
    }

    /**
     * 获取文件名称的后缀
     *
     * @param file
     * @return
     */
    public static String getFileSuffix(String file) {
        if (StringUtils.isEmpty(file)) {
            return null;
        }
        //返回index 出现的最后出现的位置
        int index = file.lastIndexOf(".");
        file = file.substring(index + 1, file.length());
        return file;
    }

    public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        String newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }


    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static void copyProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    public static List flatternJsonArray(JSONArray array){
        if (null != array && array.size() > 0) {
            List blist = new ArrayList();
            for(Object j : array){
                Map ob = new HashMap();
                JSONObject m = (JSONObject) j;
                for(String str : m.keySet()){
                    Object h = m.get(str);
                    JSONObject hm = (JSONObject) h;
                    for(Object s : hm.keySet()){
                        ob.put(s,hm.get(s));
                    }
                }
                blist.add(ob);
            }
            return blist;
        }
        return null;
    }


    public static Client buildClient(Client client,String sign, AbstractPhaseInterceptor interceptor){

        client.getRequestContext().put(Header.HEADER_LIST, createHeadersList(sign));
        client.getInInterceptors().add(interceptor);

        return client;
    }

    public static List createHeadersList(String signText){

        List headersList = new ArrayList();
        Document doc = DOMUtils.createDocument();
        Element auth = doc.createElement( "auth");
        Element sign = doc.createElement("sign");
        sign.setTextContent(signText);
        auth.appendChild(sign);
        headersList.add(new Header(new QName(""), auth));

        return headersList;
    }




}
