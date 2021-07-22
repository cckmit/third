/*
 * $Id: ObjectBatchHandleQuartzJob.java 5367 2012-03-02 08:00:13Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheduling;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.scheduling.quartz.SingleNodeQuartzJobBean;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cn.redflagsoft.base.service.ObjectBatchHandler;


/**
 * 
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class ObjectBatchHandleQuartzJob extends SingleNodeQuartzJobBean/*	implements InitializingBean, BeanNameAware */{
	private static final Log log = LogFactory.getLog(ObjectBatchHandleQuartzJob.class);
	private ObjectBatchHandler objectBatchHandler;
	private String jobName;
	/**
	 * @return the objectBatchHandler
	 */
	public ObjectBatchHandler getObjectBatchHandler() {
		return objectBatchHandler;
	}
	/**
	 * @param objectBatchHandler the objectBatchHandler to set
	 */
	public void setObjectBatchHandler(ObjectBatchHandler objectBatchHandler) {
		this.objectBatchHandler = objectBatchHandler;
	}

//	public void afterPropertiesSet() throws Exception {
//		Assert.notNull(objectBatchHandler, "objectBatchHandler is required.");
//		log.debug("启动Job： " + this);
//	}

	/**
	 * @return the jobName
	 */
	public String getJobName() {
		return jobName;
	}
	/**
	 * @param jobName the jobName to set
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	/* (non-Javadoc)
	 * @see org.opoo.apps.scheduling.quartz.SingleNodeQuartzJobBean#executeInSeniorClusterMember(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInSeniorClusterMember(JobExecutionContext arg0)
			throws JobExecutionException {
		if(jobName == null){
			jobName = ObjectBatchHandleQuartzJob.class.getSimpleName();
		}
		String enableKey = jobName + ".enabled"; 
		if(Application.isInitialized() && Application.isContextInitialized() && AppsGlobals.isSetup()){
			if(!AppsGlobals.getProperty(enableKey, false)){
				log.debug(this + " 未启用，配置运行时属性 " + enableKey + "=true 启用该Job。");
				return;
			}
			
			log.debug("开始执行Job：" + this);
			long start = System.currentTimeMillis();
			int objects = objectBatchHandler.handleObjects();
			
			long d = System.currentTimeMillis() - start;
			log.info("执行任务‘" + jobName + "’结束，本次共处理对象" + objects + "个 ，用时" + d + "毫秒。");
		}
	}

	public String toString(){
		return "ObjectBatchHandleQuartzJob[" + jobName + "]";
	}
//	/* (non-Javadoc)
//	 * @see org.springframework.beans.factory.BeanNameAware#setBeanName(java.lang.String)
//	 */
//	public void setBeanName(String name) {
//		this.beanName = name;
//	}
}
