/*
 * $Id: SchemeAction.java 5837 2012-06-05 07:32:52Z lf $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.web.struts2;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.license.annotation.ProductModule;
import org.opoo.util.Assert;

import cn.redflagsoft.base.aop.ParametersSetter;
import cn.redflagsoft.base.aop.annotation.PrintAfter;
import cn.redflagsoft.base.aop.interceptor.ParametersAware;
import cn.redflagsoft.base.scheme.Scheme;
import cn.redflagsoft.base.scheme.SchemeInvoker;
import cn.redflagsoft.base.scheme.SchemeManager;

import com.opensymphony.xwork2.ActionContext;

/**
 * @author Alex Lin
 *
 */
@ProductModule(edition = "common", module = "base")
public class SchemeAction extends AbstractModelAction/* implements ApplicationContextAware*/  {
	private static final long serialVersionUID = 9122303738590807134L;
	private static final Log log = LogFactory.getLog(SchemeAction.class);
	//private ApplicationContext applicationContext;
	private SchemeManager schemeManager;
	private String s;
	private String m;
	
//	protected Scheme getScheme(String name){
//		Assert.notBlank(name, "必须指定要执行的WorkScheme的名称。");
//		Object bean = null;
//		try {
//			bean = applicationContext.getBean(name);
//		} catch (NoSuchBeanDefinitionException e) {
//			log.error(e);
//			throw new SchemeException("找不到Scheme: " + name);
//		}
//		if(bean != null && bean instanceof Scheme){
//			return (Scheme) bean;
//		}
//		throw new SchemeException("找不到Scheme: " + name);
//	}
	
	@PrintAfter
	public String execute() throws Exception{
		Assert.notBlank(s, "必须指定要执行的 Scheme 的名称。");
		Scheme scheme = schemeManager.getScheme(s);
		
		//并不是所有的Scheme都需要参数。
		if(scheme instanceof ParametersAware){
			((ParametersAware) scheme).setParameters(ActionContext.getContext().getParameters());
			if(log.isDebugEnabled()){
				log.debug("参数在Action中：" + ParametersSetter.getParameterLogMap(ActionContext.getContext().getParameters()));
			}
		}
		
		//scheme.setParameters(ActionContext.getContext().getParameters());
		
		Object result = SchemeInvoker.invoke(scheme, m);
		
		//executeResultToModel(result);
		setModelResult(result);
		
		return SUCCESS;
	}


//	/* (non-Javadoc)
//	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
//	 */
//	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//		this.applicationContext = applicationContext;
//	}
	
	public void setSchemeManager(SchemeManager schemeManager) {
		this.schemeManager = schemeManager;
	}
	
	/**
	 * @return the s
	 */
	public String getS() {
		return s;
	}

	/**
	 * @param s the s to set
	 */
	public void setS(String s) {
		this.s = s;
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
}
