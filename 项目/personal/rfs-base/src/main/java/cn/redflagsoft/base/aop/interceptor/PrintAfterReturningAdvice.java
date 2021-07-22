/*
 * $Id: PrintAfterReturningAdvice.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.aop.interceptor;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.AfterReturningAdvice;

import cn.redflagsoft.base.aop.PrintDataHandler;
import cn.redflagsoft.base.web.struts2.interceptor.Printable;

/**
 * ��ӡ������������
 * 
 * ���ڶ�ACTION�����ء�
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
			log.debug(target.getClass() + "."+ method.getName() + "  ִ�к�: " + returnValue);
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
			log.debug(target.getClass() + "."+ method.getName() + "  ִ�к�: " + returnValue);
		}
		if(target != null && Action.SUCCESS.equals(returnValue) && target instanceof Printable){
			Printable action = (Printable) target;
			String printConfig = action.getPrintConfig();
			boolean xlsExport = action.isXlsExport();
			Model model = action.getModel();
			
			if(StringUtils.isNotBlank(printConfig) && model != null){
				//�����ӡ��صĲ�����
				//���ڼ�result���ܸı䡣
				
				if(log.isDebugEnabled()){
					log.debug("�����ӡ���ݣ�" + model + ", ��������" + model.getTotal());
				}
				
				String profileKey = "�����ӡ���ݣ�";
				
				try{
					UtilTimerStack.push(profileKey);
					PrintHandler ph = Application.getContext().get("printHandler", PrintHandler.class);
					Model m = ph.preparePrint(printConfig, model, xlsExport);
					//�����ڵĴ������滻ԭ���Ĵ�������
					action.setModel(m);
				}catch(Exception e){
					model.setMessage(false, "�����ӡ���ݳ���" + e.getMessage(), null);
					if(log.isDebugEnabled()){
						log.debug("�����ӡ���ݳ���", e);
					}
				}finally{
					UtilTimerStack.pop(profileKey);
				}
				
			}else{
				if(log.isDebugEnabled()){
					log.debug("�����ϴ�ӡ������config=" + printConfig + ", model=" + model);
				}
			}
		}
	}
 
 */

}
