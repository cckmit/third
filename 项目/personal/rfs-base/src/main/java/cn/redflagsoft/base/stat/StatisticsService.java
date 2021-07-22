/*
 * $Id: StatisticsService.java 6179 2013-01-23 07:20:33Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.stat;

import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class StatisticsService implements StatisticsServiceMBean {
	private static final Log log = LogFactory.getLog(StatisticsService.class);
	
	Statistics stat = new StatisticsImpl();
	
	/**
	 * @param stat the stat to set
	 */
	public void setStatistics(Statistics stat) {
		this.stat = stat;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.stat.Statistics#isStatisticsEnabled()
	 */
	public boolean isStatisticsEnabled() {
		return stat.isStatisticsEnabled();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.stat.Statistics#setStatisticsEnabled(boolean)
	 */
	public void setStatisticsEnabled(boolean b) {
		stat.setStatisticsEnabled(b);
		log.debug("通过MBean设置StatisticsEnabled属性：" + b);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.stat.Statistics#clear()
	 */
	public void clear() {
		stat.clear();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.stat.Statistics#getStartTime()
	 */
	public long getStartTime() {
		return stat.getStartTime();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.stat.Statistics#getSchemeExecutionCount()
	 */
	public long getSchemeExecutionCount() {
		return stat.getSchemeExecutionCount();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.stat.Statistics#setOperationThreshold(long)
	 */
	public void setOperationThreshold(long threshold) {
		stat.setOperationThreshold(threshold);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.stat.Statistics#getOperationThreshold()
	 */
	public long getOperationThreshold() {
		return stat.getOperationThreshold();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.stat.Statistics#logSummary()
	 */
	public void logSummary() {
		stat.logSummary();
	}
	
	public void printSummary() {
		System.out.println("输出系统统计信息 ....");
		System.out.println("开始时间: " + new Date(stat.getStartTime()));
		System.out.println("Scheme 执行次数: " + stat.getSchemeExecutionCount());
		String[] names = stat.getSchemeNames();
		for(String name: names){
			SchemeStatistics statistics = stat.getSchemeStatistics(name);
			System.out.println(statistics.toFormatString());
		}
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.stat.Statistics#getSchemeNames()
	 */
	public String[] getSchemeNames() {
		return stat.getSchemeNames();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.stat.Statistics#getSchemeStatistics(java.lang.String)
	 */
	public SchemeStatistics getSchemeStatistics(String schemeName) {
		return stat.getSchemeStatistics(schemeName);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.stat.Statistics#getSchemeStatMap(java.lang.String)
	 */
	public Map<String, Long> getSchemeStatisticsMap(String schemeName) {
		return stat.getSchemeStatisticsMap(schemeName);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.stat.Statistics#getSchemeExecutionMaxTime()
	 */
	public long getSchemeExecutionMaxTime() {
		return stat.getSchemeExecutionMaxTime();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.stat.Statistics#getSchemeExecutionMaxTimeSchemeInfo()
	 */
	public String getSchemeExecutionMaxTimeSchemeInfo() {
		return stat.getSchemeExecutionMaxTimeSchemeInfo();
	}
}
