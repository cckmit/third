/*
 * $Id: Client.java 6361 2014-04-04 02:28:58Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.ws.rest.httpclient;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import cn.redflagsoft.base.ws.rest.types.Result;



/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class Client {
	private HttpClient client;
	private HttpMethod m;
	
	private Client(){
		this.client = new HttpClient();
	}
	
	public static Client post(String url){
		Client client = new Client();
		client.m = new PostMethod(url);
		return client;
	}
	
	public static Client get(String url){
		Client client = new Client();
		client.m = new GetMethod(url);
		return client;
	}
	
	public Client addParam(String name, String value){
		if(m instanceof PostMethod){
			((PostMethod)m).addParameter(name, value);
		}
		return this;
	}
	
//	public HttpClient getHttpClient(){
//		return client;
//	}
//	
//	public HttpMethod getMethod(){
//		return m;
//	}
	
	@SuppressWarnings("unchecked")
	public <T extends Result> T execute(Class<T> resultType) throws HttpException, IOException{
		try{
			int status = client.executeMethod(m);
			if(status != HttpStatus.SC_OK){
				throw new IOException("δ����Ԥ�ڵ�״̬��" + status);
			}
			
			if(resultType == null){
				resultType = (Class<T>) Result.class;
			}
	    	
			T result = HttpClientUtils.read(m.getResponseBodyAsStream(), resultType);//objectMapper.readValue(m.getResponseBodyAsStream(), resultType);
			if(result.isSuccess()){
				//log.info("SUCCESS��" + result.getMessage());
	    		return result;
	    	}else{
	    		throw new IOException(result.getMessage());
	    	}
		}finally{
			m.releaseConnection();
		}
	}
}
