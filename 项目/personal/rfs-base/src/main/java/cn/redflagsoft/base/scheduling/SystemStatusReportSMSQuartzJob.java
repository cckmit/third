/*
 * $Id: SystemStatusReportSMSQuartzJob.java 6342 2013-12-16 01:56:08Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.scheduling;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.scheduling.quartz.SingleNodeQuartzJobBean;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cn.redflagsoft.base.service.RiskService;
import cn.redflagsoft.base.service.TaskService;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class SystemStatusReportSMSQuartzJob extends SingleNodeQuartzJobBean {
	public static final String JOB_ENABLED = "SystemStatusReportSMSQuartzJob.enabled";
	private static final Log log = LogFactory.getLog(SystemStatusReportSMSQuartzJob.class);
	private static final DateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");

	/* (non-Javadoc)
	 * @see org.opoo.apps.scheduling.quartz.SingleNodeQuartzJobBean#executeInSeniorClusterMember(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInSeniorClusterMember(JobExecutionContext ctx) throws JobExecutionException {
		if(Application.isInitialized() && Application.isContextInitialized() && AppsGlobals.isSetup()
				&& AppsGlobals.getProperty(JOB_ENABLED, true)){
			log.debug("ִ��Job��" + this);
			
			//���Ҹ�����
			TaskService taskService = Application.getContext().get("taskService", TaskService.class);
			RiskService riskService = Application.getContext().get("riskService", RiskService.class);
			cn.redflagsoft.base.stat.StatisticsService schemeStatisticsMBean = Application.getContext().get("schemeStatisticsMBean", cn.redflagsoft.base.stat.StatisticsService.class);
			org.opoo.apps.query.stat.StatisticsService queryStatisticsMBean = Application.getContext().get("queryStatisticsMBean", org.opoo.apps.query.stat.StatisticsService.class);
			org.hibernate.jmx.StatisticsService hibernateStatisticsMBean = Application.getContext().get("hibernateStatisticsMBean", org.hibernate.jmx.StatisticsService.class);
			
			String msg = String.format("Now:%s,Task:%s,Risk:%s,Scheme:%s,Query:%s,PrepareStatementCount:%s,TransactionCount:%s,QueryExecutionCount:%s.", 
					format(new Date()), 
					format(taskService.getLastCalculateAllRunningTasksTimeUsedTime()),
					format(riskService.getLastCalculateAllRunningRisksTime()),
					schemeStatisticsMBean != null ? schemeStatisticsMBean.getSchemeExecutionCount() : -1,
					queryStatisticsMBean != null ? queryStatisticsMBean.getQueryExecutionCount() : -1,
					hibernateStatisticsMBean != null ? hibernateStatisticsMBean.getPrepareStatementCount() : -1,
					hibernateStatisticsMBean != null ? hibernateStatisticsMBean.getTransactionCount() : -1,
					hibernateStatisticsMBean != null ? hibernateStatisticsMBean.getQueryExecutionCount() : -1);
			
			String nodeInfo = getNodeInfo();
			if(nodeInfo != null){
				msg += "(" + nodeInfo + ")";
			}
			
			log.info("System status: " + msg);
			sendShortMessage(msg);
		}
	}
	
	private static String format(Date date){
		if(date == null){
			return "";
		}
		return f.format(date);
	}
	
	private String getNodeInfo(){
		try{
			return com.tangosol.net.CacheFactory.ensureCluster().getLocalMember().getAddress().toString();
		}catch(Throwable e){
			log.warn(e.getMessage());
			return null;
		}
	}
	
	private void sendShortMessage(String content){
		//smsSender.send
		//send
		String string = AppsGlobals.getProperty("system.status.report.sms.sendMethod");
		if(StringUtils.isBlank(string)){
			log.warn("δ���� system.status.report.sms.sendMethod��������ϵͳϵͳ����״̬���档");
			return;
		}
		String number = AppsGlobals.getProperty("system.status.report.sms.receivers");
		if(StringUtils.isBlank(number)){
			log.warn("δ���� system.status.report.sms.receivers��������ϵͳϵͳ����״̬���档");
			return;
		}
		
		int pos = string.lastIndexOf('.');
		if(pos == -1){
			log.warn(" system.status.report.sms.sendMethod����ȷ��������ϵͳ����״̬���档");
			return;
		}
		
		String beanName = string.substring(0, pos);
		String methodName = string.substring(pos + 1);
		
		try {
			Object object = Application.getContext().get(beanName, Object.class);
			Method method = object.getClass().getMethod(methodName, String.class, String.class);
			Object invoke = method.invoke(object, number, content);
			
			log.debug("״̬���淢���� " + number + ", ���أ�" + invoke);
		} catch (Exception e) {
			log.error("ϵͳ����״̬���淢��ʧ��", e);
		}
	}
}
