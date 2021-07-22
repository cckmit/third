/*
 * $Id: MethodStatistics.java 6179 2013-01-23 07:20:33Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.stat;

import java.io.Serializable;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class MethodStatistics implements Serializable{
	private static final long serialVersionUID = 4317451231907341690L;
	private final String methodName;
	private long executionCount;
	private long executionAvgTime;
	private long executionMaxTime;
	private long executionMinTime = Long.MAX_VALUE;

	MethodStatistics(String methodName) {
		this.methodName = methodName;
	}
	
	public String getMethodName(){
		return methodName;
	}

	/**
	 * queries executed to the DB
	 */
	public long getExecutionCount() {
		return executionCount;
	}
	
	
	/**
	 * average time in ms taken by the excution of this query onto the DB
	 */
	public long getExecutionAvgTime() {
		return executionAvgTime;
	}

	/**
	 * max time in ms taken by the excution of this query onto the DB
	 */
	public long getExecutionMaxTime() {
		return executionMaxTime;
	}
	
	/**
	 * min time in ms taken by the excution of this query onto the DB
	 */
	public long getExecutionMinTime() {
		return executionMinTime;
	}
	
	/**
	 * add statistics report of a DB query
	 * 
	 * @param rows rows count returned
	 * @param time time taken
	 */
	void executed(long time) {
		if (time < executionMinTime) executionMinTime = time;
		if (time > executionMaxTime) executionMaxTime = time;
		executionAvgTime = ( executionAvgTime * executionCount + time ) / ( executionCount + 1 );
		executionCount++;
	}

	public String toString() {
		return new StringBuffer()
				.append( "MethodStatistics" )
				.append( "[methodName=" ).append( this.methodName )
				.append( ",executionCount=" ).append( this.executionCount )
				.append( ",executionAvgTime=" ).append( this.executionAvgTime )
				.append( ",executionMaxTime=" ).append( this.executionMaxTime )
				.append( ",executionMinTime=" ).append( this.executionMinTime )
				.append( ']' )
				.toString();
	}
}
