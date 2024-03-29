/*
 * $Id: StatisticsImpl.java 6180 2013-01-23 07:34:33Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.stat;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.Maps;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class StatisticsImpl implements Statistics, StatisticsImplementor {
	private static final Log log = LogFactory.getLog(StatisticsImpl.class);
	
	private Map<String, SchemeStatistics> schemeStatistics = Maps.newLinkedHashMap();
	
	private boolean statisticsEnabled;
	
	private long startTime = System.currentTimeMillis();
	private long schemeExecutionCount;
	private long schemeExecutionMaxTime;
	private String schemeExecutionMaxTimeSchemeInfo;
	
	private long operationThreshold = 0;    // log operations that take longer than this value (in milliseconds)
     										// We don't log anything if operationThreshold == 0 

	/**
	 * 
	 */
	public StatisticsImpl() {
		clear();
	}

	/**
	 * reset all statistics
	 */
	public synchronized void clear() {
		schemeStatistics.clear();
		schemeExecutionCount = 0;
		schemeExecutionMaxTime = 0;
		schemeExecutionMaxTimeSchemeInfo = null;
		
		startTime = System.currentTimeMillis();
	}
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.stat.Statistics#isStatisticsEnabled()
	 */
	public boolean isStatisticsEnabled() {
		return statisticsEnabled;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.stat.Statistics#setStatisticsEnabled(boolean)
	 */
	public void setStatisticsEnabled(boolean b) {
		statisticsEnabled = b;
	}
	
	public void schemeExecuted(String schemeName, String methodName, long time){
		synchronized(this) {
			schemeExecutionCount++;
			if(schemeExecutionMaxTime < time){
				schemeExecutionMaxTime = time;
				schemeExecutionMaxTimeSchemeInfo = schemeName + "!" + methodName;
			}
			getSchemeStatistics(schemeName).methodExecuted(methodName, time);
        }
		if (operationThreshold > 0 && operationThreshold < time) {
			logOperation("execute ", schemeName + "#" + methodName, time);
		}
	}
	

	public synchronized SchemeStatistics getSchemeStatistics(String schemeName){
		SchemeStatistics s = (SchemeStatistics) schemeStatistics.get(schemeName);
		if (s == null) {
			s = new SchemeStatistics(schemeName);
			schemeStatistics.put(schemeName, s);
		}
		return s;
	}
	
	public Map<String,Long> getSchemeStatisticsMap(String schemeName){
		SchemeStatistics statistics = getSchemeStatistics(schemeName);
		Collection<MethodStatistics> values = statistics.methodStatistics.values();
		Map<String, Long> map = Maps.newHashMap();
		for(MethodStatistics cs:values){
			map.put(cs.getMethodName(), cs.getExecutionCount());
		}
		return map;
	}
	
	public long getStartTime(){
		return startTime;
	}
	
	public long getSchemeExecutionCount(){
		return schemeExecutionCount;
	}
	
	/**
	 * @return the schemeExecutionMaxTime
	 */
	public long getSchemeExecutionMaxTime() {
		return schemeExecutionMaxTime;
	}

	/**
	 * @return the schemeExecutionMaxTimeSchemeInfo
	 */
	public String getSchemeExecutionMaxTimeSchemeInfo() {
		return schemeExecutionMaxTimeSchemeInfo;
	}

	public synchronized void setOperationThreshold(long threshold) {
		operationThreshold = threshold;
	}

	public synchronized long getOperationThreshold() {
		return operationThreshold;
	}
	
	public String[] getSchemeNames(){
		return schemeStatistics.keySet().toArray(new String[schemeStatistics.size()]);
	}
	
	private void logOperation(String operation, String entityName, long time) {
		if (entityName != null)
			log.info(operation + entityName + " " + time + "ms");
		else
			log.info(operation); // just log that the event occurred
	}
	
	public void logSummary() {
		log.info("Logging statistics....");
		log.info("start time: " + startTime);
		log.info("scheme execution count: " + schemeExecutionCount);
		for(SchemeStatistics s: schemeStatistics.values()){
			log.info(s.toFormatString());
		}
	}
	
	public String toString(){
		return new StringBuffer()
		.append("Statistics[")
		.append("start time=").append(startTime)
		.append(",scheme execution count=").append(schemeExecutionCount)
		.append("]")
		.toString();
	}
}
