/*
 * $Id: PrintInterceptor.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.web.struts2.interceptor;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.Model;
import org.opoo.apps.lifecycle.Application;

import cn.redflagsoft.base.service.PrintHandler;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * @author Alex Lin
 *
 */
public class PrintInterceptor extends AbstractInterceptor {
	private static final Log log = LogFactory.getLog(PrintInterceptor.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8962709910338724198L;

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.interceptor.AbstractInterceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String result = invocation.invoke();
		if(Action.SUCCESS.equals(result)){
			Object action = invocation.getAction();
			log.debug("执行打印拦截器，action：" + action);
			if(action instanceof Printable){
				Printable proxy = (Printable) action;
				if(StringUtils.isNotBlank(proxy.getPrintConfig()) && proxy.getModel() != null){
					//处理打印相关的操作。
					//这期间result可能改变。
					if(log.isDebugEnabled()){
						log.debug("处理打印数据：" + proxy.getModel() + ", 数据量：" + proxy.getModel().getTotal());
					}
					
					try{
						PrintHandler ph = Application.getContext().get("printHandler", PrintHandler.class);
						Model model = ph.preparePrint(proxy.getPrintConfig(), proxy.getModel());
						//以现在的处理结果替换原来的处理结果。
						proxy.setModel(model);
					}catch(Exception e){
						proxy.getModel().setMessage(false, "处理打印数据出错：" + e.getMessage(), null);
						if(log.isDebugEnabled()){
							log.debug("处理打印数据出错", e);
						}
					}
				}
			}
		}
		
		if(log.isDebugEnabled()){
			log.debug("打印拦截器执行完毕！");
		}
		return result;
	}
}
