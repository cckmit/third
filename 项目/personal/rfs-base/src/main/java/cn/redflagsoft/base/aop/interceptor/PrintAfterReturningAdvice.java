/*
 * $Id: PrintAfterReturningAdvice.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.aop.interceptor;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.AfterReturningAdvice;

import cn.redflagsoft.base.aop.PrintDataHandler;
import cn.redflagsoft.base.web.struts2.interceptor.Printable;

/**
 * 打印后置拦截器。
 * 
 * 用在对ACTION的拦截。
 * @author Alex Lin
 *
 */
public class PrintAfterReturningAdvice extends PrintDataHandler implements AfterReturningAdvice{
	private static final Log log = LogFactory.getLog(PrintAfterReturningAdvice.class);
	
	/* (non-Javadoc)
	 * @see org.springframework.aop.AfterReturningAdvice#afterReturning(java.lang.Object, java.lang.reflect.Method, java.lang.Object[], java.lang.Object)
	 */
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		if(method.getName().startsWith("get")){
			return;
		}
		if(log.isDebugEnabled()){
			log.debug(target.getClass() + "."+ method.getName() + "  执行后: " + returnValue);
		}
		if(target != null && target instanceof Printable){
			super.preparePrintDataIfRequired(returnValue, (Printable) target);
		}
	}
	
	
/**
 public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		if(method.getName().startsWith("get")){
			return;
		}
		
		if(log.isDebugEnabled()){
			log.debug(target.getClass() + "."+ method.getName() + "  执行后: " + returnValue);
		}
		if(target != null && Action.SUCCESS.equals(returnValue) && target instanceof Printable){
			Printable action = (Printable) target;
			String printConfig = action.getPrintConfig();
			boolean xlsExport = action.isXlsExport();
			Model model = action.getModel();
			
			if(StringUtils.isNotBlank(printConfig) && model != null){
				//处理打印相关的操作。
				//这期间result可能改变。
				
				if(log.isDebugEnabled()){
					log.debug("处理打印数据：" + model + ", 数据量：" + model.getTotal());
				}
				
				String profileKey = "处理打印数据：";
				
				try{
					UtilTimerStack.push(profileKey);
					PrintHandler ph = Application.getContext().get("printHandler", PrintHandler.class);
					Model m = ph.preparePrint(printConfig, model, xlsExport);
					//以现在的处理结果替换原来的处理结果。
					action.setModel(m);
				}catch(Exception e){
					model.setMessage(false, "处理打印数据出错：" + e.getMessage(), null);
					if(log.isDebugEnabled()){
						log.debug("处理打印数据出错", e);
					}
				}finally{
					UtilTimerStack.pop(profileKey);
				}
				
			}else{
				if(log.isDebugEnabled()){
					log.debug("不符合打印条件，config=" + printConfig + ", model=" + model);
				}
			}
		}
	}
 
 */

}
