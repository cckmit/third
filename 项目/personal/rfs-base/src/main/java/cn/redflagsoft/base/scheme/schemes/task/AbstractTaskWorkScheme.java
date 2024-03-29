/*
 * $Id: AbstractTaskWorkScheme.java 5284 2011-12-27 04:00:34Z lf $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme.schemes.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.lifecycle.Application;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.scheme.AbstractWorkScheme;
import cn.redflagsoft.base.scheme.SchemeException;
import cn.redflagsoft.base.service.ObjectService;
import cn.redflagsoft.base.service.impl.AbstractRFSObjectService;

/**
 * 业务（Task）管理的基类。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public abstract class AbstractTaskWorkScheme extends AbstractWorkScheme implements ApplicationContextAware {
	private static final Log log = LogFactory.getLog(AbstractTaskWorkScheme.class);
	
	private String note;//备注
	private String reasonCategory;//原因类别
	private String reason;//原因
	private String objectServiceName;
	
	private String s0; //原因类别    （有的表单中使用的是  ‘reasonCategory’ 有的用的 ‘s0’ 字段 ）
	private String s1; //原因	    （有的表单中使用的是  ‘reason’ 有的用的 ‘s1’ 字段 ）
	
	public String getS0() {
		return s0;
	}

	public void setS0(String s0) {
		this.s0 = s0;
	}

	public String getS1() {
		return s1;
	}

	public void setS1(String s1) {
		this.s1 = s1;
	}

	private ApplicationContext applicationContext;
	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @return the reasonCategory
	 */
	public String getReasonCategory() {
		return reasonCategory;
	}

	/**
	 * @param reasonCategory the reasonCategory to set
	 */
	public void setReasonCategory(String reasonCategory) {
		this.reasonCategory = reasonCategory;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	

	public void setObjectServiceName(String objectServiceName){
		this.objectServiceName = objectServiceName;
//		Assert.notNull(objectServiceName, "objectServiceName is required.");
//		ObjectService<?> service = Application.getContext().get(objectServiceName, ObjectService.class);
//		if(service != null){
//			setObjectService(service);
//			log.debug("Set objectService by name '" + objectServiceName + "' : " + service);
//		}else{
//			throw new IllegalArgumentException("Cannot find ObjectService for name '" + objectServiceName + "'.");
//		}
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.AbstractWorkScheme#prepare()
	 */
	@Override
	public void prepare() {
		//Assert.notNull(objectServiceName, "objectServiceName is required.");
		if(objectServiceName != null){
			ObjectService<?> service = Application.getContext().get(objectServiceName, ObjectService.class);
			if(service != null){
				setObjectService(service);
				log.debug("Set objectService by name '" + objectServiceName + "' : " + service);
			}else{
				//throw new IllegalArgumentException("Cannot find ObjectService for name '" + objectServiceName + "'.");
				log.warn("Cannot find ObjectService for name '" + objectServiceName + "'.");
				//log.warn("objectServiceName没有配置，使用resolveObjectService解析objectService，但必须指定taskSN或者workSN。");
			}
		}else{
			log.warn("objectServiceName没有配置，使用resolveObjectService解析objectService，但必须指定taskSN或者workSN。");
		}
		super.prepare();
	}
	

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.AbstractWorkScheme#resolveObjectService(int, java.lang.Long)
	 */
	@Override
	protected ObjectService<RFSObject> resolveObjectService(int objectType,	Long objectId) {
		String[] names = applicationContext.getBeanNamesForType(AbstractRFSObjectService.class);
		for(String name:names){
			AbstractRFSObjectService service = Application.getContext().get(name, AbstractRFSObjectService.class);
			if(service.getObjectType() == objectType){
				return service;
			}
		}
		
		throw new SchemeException("无法解析对应的ObjectService(objectType=" + objectType + ", objectId=" + objectId + ")");
	}

//	/* (non-Javadoc)
//	 * @see cn.redflagsoft.base.scheme.AbstractWorkScheme#afterPropertiesSet()
//	 */
//	@Override
//	public void afterPropertiesSet() throws Exception {
//		//先设置一个无效的ObjectService占位。
//		setObjectService(new ObjectService.InvalidObjectService<RFSObject>());
//		super.afterPropertiesSet();
//	}

	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
