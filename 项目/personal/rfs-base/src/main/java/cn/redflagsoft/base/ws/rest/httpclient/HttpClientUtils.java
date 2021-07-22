/*
 * $Id: HttpClientUtils.java 6405 2014-05-16 00:58:43Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.ws.rest.httpclient;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import cn.redflagsoft.base.ws.rest.types.Result;


/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public abstract class HttpClientUtils {
	private static final Log log = LogFactory.getLog(HttpClientUtils.class);
	private static ObjectMapper objectMapper;

	public static <T> T read(InputStream is, Class<T> typpe) throws JsonParseException, JsonMappingException, IOException{
		if(objectMapper == null){
			objectMapper = new ObjectMapper();
    		objectMapper.setVisibility(JsonMethod.IS_GETTER, Visibility.ANY);
    		objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		}
    	return objectMapper.readValue(is, typpe);
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Result> T execute(HttpClient client, HttpMethod m, Class<T> resultType) throws Exception{
		//HttpClient client = new HttpClient();
		try{
			int status = client.executeMethod(m);
			if(status != HttpStatus.SC_OK){
				throw new Exception("未返回预期的状态：" + status);
			}
			
			if(resultType == null){
				resultType = (Class<T>) Result.class;
			}
	    	
			T result = read(m.getResponseBodyAsStream(), resultType);//objectMapper.readValue(m.getResponseBodyAsStream(), resultType);
			if(result.isSuccess()){
				log.info("SUCCESS：" + result.getMessage());
	    		return result;
	    	}else{
	    		throw new Exception(result.getMessage());
	    	}
		}finally{
			m.releaseConnection();
		}
	}
	
	public static <T extends Result> T get(String url, Class<T> resultType, Callback action) throws Exception{
		HttpClient client = new HttpClient();
		GetMethod m = new GetMethod(url);
		if(action != null){
			action.beforeExecute(client, m);
		}
		return execute(client, m, resultType);
	}
	
	public static <T extends Result> T post(String url, Class<T> resultType, Callback action) throws Exception{
		HttpClient client = new HttpClient();
		PostMethod m = new PostMethod(url);
		m.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8"); 
		if(action != null){
			action.beforeExecute(client, m);
		}
		return execute(client, m, resultType);
	}
}
