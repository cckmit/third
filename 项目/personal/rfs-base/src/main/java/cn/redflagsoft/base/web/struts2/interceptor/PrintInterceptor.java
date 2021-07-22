/*
 * $Id: PrintInterceptor.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
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
			log.debug("ִ�д�ӡ��������action��" + action);
			if(action instanceof Printable){
				Printable proxy = (Printable) action;
				if(StringUtils.isNotBlank(proxy.getPrintConfig()) && proxy.getModel() != null){
					//�����ӡ��صĲ�����
					//���ڼ�result���ܸı䡣
					if(log.isDebugEnabled()){
						log.debug("�����ӡ���ݣ�" + proxy.getModel() + ", ��������" + proxy.getModel().getTotal());
					}
					
					try{
						PrintHandler ph = Application.getContext().get("printHandler", PrintHandler.class);
						Model model = ph.preparePrint(proxy.getPrintConfig(), proxy.getModel());
						//�����ڵĴ������滻ԭ���Ĵ�������
						proxy.setModel(model);
					}catch(Exception e){
						proxy.getModel().setMessage(false, "�����ӡ���ݳ���" + e.getMessage(), null);
						if(log.isDebugEnabled()){
							log.debug("�����ӡ���ݳ���", e);
						}
					}
				}
			}
		}
		
		if(log.isDebugEnabled()){
			log.debug("��ӡ������ִ����ϣ�");
		}
		return result;
	}
}
