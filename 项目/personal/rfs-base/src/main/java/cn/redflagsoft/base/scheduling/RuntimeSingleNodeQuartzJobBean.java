/*
 * $Id: RuntimeSingleNodeQuartzJobBean.java 6085 2012-10-30 00:52:54Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
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
 * �ڼ�Ⱥ���ڵ㡢����ʱִ�е�Job���ࡣ
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
	 * �Ƿ�Ҫʹ������ʱ���������á��������Job
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
			log.debug("��������ʱ״̬����ִ��Job��" + clazz.getName());
			return;
		}

		
		boolean enabled = true;
		//���Ҫ�������õ�����
		if(useRuntimeConfig){
			String propertyName = clazz.getSimpleName() + ".enabled";
			if(AppsGlobals.getProperty(propertyName, false)){
				enabled = true;
			}else{
				enabled = false;
				log.warn("Job(" + clazz.getName() + ")δ���ã�����������ʱ���� " + propertyName + " = true �����á�");
			}
		}
		
		if(enabled){
			executeInSeniorClusterMemberRuntime(context);
		}
	}

	protected void executeInSeniorClusterMemberRuntime(JobExecutionContext context) throws JobExecutionException {
	}
}
