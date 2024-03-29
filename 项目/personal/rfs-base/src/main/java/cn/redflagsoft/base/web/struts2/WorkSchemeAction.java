/*
 * $Id: WorkSchemeAction.java 5837 2012-06-05 07:32:52Z lf $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.web.struts2;

import org.opoo.util.Assert;

import cn.redflagsoft.base.aop.annotation.PrintAfter;
import cn.redflagsoft.base.scheme.Scheme;
import cn.redflagsoft.base.scheme.SchemeException;
import cn.redflagsoft.base.scheme.SchemeInvoker;
import cn.redflagsoft.base.scheme.SchemeManager;
import cn.redflagsoft.base.scheme.WorkScheme;

import com.opensymphony.xwork2.ActionContext;

/**
 * @author Alex Lin
 * @deprecated
 */
public class WorkSchemeAction extends AbstractModelAction/* implements ApplicationContextAware*/ {
	private static final long serialVersionUID = -528138721415065377L;
//	private static final Log log = LogFactory.getLog(WorkSchemeAction.class);
//	private ApplicationContext applicationContext;
	private SchemeManager schemeManager;
	private String wsn;
	private String m;
	
	
	public void setSchemeManager(SchemeManager schemeManager) {
		this.schemeManager = schemeManager;
	}


	/**
	 * 
	 * @return
	 */
	@PrintAfter
	public String process(){
//		try {
			WorkScheme ws = getWorkScheme(wsn);
			ws.setParameters(ActionContext.getContext().getParameters());
			Object result = ws.process();
			
			//executeResultToModel(result);
			setModelResult(result);
			
//		} catch (Exception e) {
//			log.error(e);
//			model.setException(e);
//			return SUCCESS;
//		}
		
		return SUCCESS;
	}
	
	
	/**
	 * 
	 */
	
	@PrintAfter
	public String execute() throws Exception{
//		try {
			WorkScheme ws = getWorkScheme(wsn);
			
			ws.setParameters(ActionContext.getContext().getParameters());
			
			Object result = SchemeInvoker.invoke(ws, m);
			
			//executeResultToModel(result);
			setModelResult(result);
			
//		} catch (Exception e) {
//			log.error(e);
//			model.setException(e);
//			return SUCCESS;
//		}
		
		return SUCCESS;
	}


	/**
	 * @return the wsn
	 */
	public String getWsn() {
		return wsn;
	}

	/**
	 * @param wsn the wsn to set
	 */
	public void setWsn(String wsn) {
		this.wsn = wsn;
	}

	/**
	 * @return the m
	 */
	public String getM() {
		return m;
	}

	/**
	 * @param m the m to set
	 */
	public void setM(String m) {
		this.m = m;
	}


//	/* (non-Javadoc)
//	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
//	 */
//	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
//		applicationContext = arg0;
//	}
//	
//	
//	protected WorkScheme getWorkScheme(String name){
//		Assert.notBlank(name, "必须指定要执行的WorkScheme的名称。");
//		Object bean = null;
//		try {
//			bean = applicationContext.getBean(name);
//		} catch (NoSuchBeanDefinitionException e) {
//			log.error(e);
//			throw new SchemeException("找不到WorkScheme: " + name);
//		}
//		if(bean != null && bean instanceof WorkScheme){
//			return (WorkScheme) bean;
//		}
//		throw new SchemeException("找不到WorkScheme: " + name);
//	}
	
	protected WorkScheme getWorkScheme(String name){
		Assert.notBlank(name, "必须指定要执行的WorkScheme的名称。");
		Scheme scheme = schemeManager.getScheme(name);
		if(scheme instanceof WorkScheme){
			return (WorkScheme) scheme;
		}
		throw new SchemeException("找不到WorkScheme: " + name);
	}
}
