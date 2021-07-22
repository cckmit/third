/*
 * $Id: RuntimeSingleNodeQuartzJobBean.java 6085 2012-10-30 00:52:54Z lcj $
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

/**
 * 在集群单节点、运行时执行的Job超类。
 * 
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class RuntimeSingleNodeQuartzJobBean extends SingleNodeQuartzJobBean {
	private static final Log log = LogFactory.getLog(RuntimeSingleNodeQuartzJobBean.class);
	
	/**
	 * @param useRuntimeConfig the useRuntimeConfig to set
	 */
	public void setUseRuntimeConfig(boolean useRuntimeConfig) {
		this.useRuntimeConfig = useRuntimeConfig;
	}

	/**
	 * 是否要使用运行时属性来启用、禁用这个Job
	 */
	private boolean useRuntimeConfig = true;
	/* (non-Javadoc)
	 * @see org.opoo.apps.scheduling.quartz.SingleNodeQuartzJobBean#executeInSeniorClusterMember(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInSeniorClusterMember(JobExecutionContext context) throws JobExecutionException {
		Class<?> clazz = getClass();
		boolean isRuntime = Application.isInitialized() && Application.isContextInitialized() && AppsGlobals.isSetup();
		if(!isRuntime){
			log.debug("不是运行时状态，不执行Job：" + clazz.getName());
			return;
		}

		
		boolean enabled = true;
		//如果要根据配置的属性
		if(useRuntimeConfig){
			String propertyName = clazz.getSimpleName() + ".enabled";
			if(AppsGlobals.getProperty(propertyName, false)){
				enabled = true;
			}else{
				enabled = false;
				log.warn("Job(" + clazz.getName() + ")未启用，请配置运行时属性 " + propertyName + " = true 来启用。");
			}
		}
		
		if(enabled){
			executeInSeniorClusterMemberRuntime(context);
		}
	}

	protected void executeInSeniorClusterMemberRuntime(JobExecutionContext context) throws JobExecutionException {
	}
}
