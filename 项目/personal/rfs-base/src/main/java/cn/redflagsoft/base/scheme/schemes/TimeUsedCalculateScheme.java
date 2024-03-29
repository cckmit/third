/*
 * $Id: TimeUsedCalculateScheme.java 6239 2013-06-13 02:05:24Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme.schemes;

import java.util.Date;

import org.opoo.apps.AppsGlobals;

import cn.redflagsoft.base.scheme.AbstractScheme;
import cn.redflagsoft.base.scheme.SchemeException;
import cn.redflagsoft.base.util.DateUtil;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class TimeUsedCalculateScheme extends AbstractScheme {
	private Date beginTime;
	private Date endTime;
	private Integer offset;
	private int timeUnitFlag = 4;//4-日(工作日)、5-天（自然天）
	private boolean includeFirstDay = false;
	
	
	/**
	 * @return the beginTime
	 */
	public Date getBeginTime() {
		return beginTime;
	}


	/**
	 * @param beginTime the beginTime to set
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}


	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}


	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}


	/**
	 * @return the offset
	 */
	public Integer getOffset() {
		return offset;
	}


	/**
	 * @param offset the offset to set
	 */
	public void setOffset(Integer offset) {
		this.offset = offset;
	}


	/**
	 * @return the timeUnitFlag
	 */
	public int getTimeUnitFlag() {
		return timeUnitFlag;
	}


	/**
	 * @param timeUnitFlag the timeUnitFlag to set
	 */
	public void setTimeUnitFlag(int timeUnitFlag) {
		this.timeUnitFlag = timeUnitFlag;
	}


	/**
	 * @return the includeFirstDay
	 */
	public boolean isIncludeFirstDay() {
		return includeFirstDay;
	}


	/**
	 * @param includeFirstDay the includeFirstDay to set
	 */
	public void setIncludeFirstDay(boolean includeFirstDay) {
		this.includeFirstDay = includeFirstDay;
	}


	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.AbstractScheme#doScheme()
	 */
	@Override
	public Object doScheme() throws SchemeException {
		if(beginTime == null){
			throw new SchemeException("开始时间不能为空。");
		}
		if(timeUnitFlag != 4 && timeUnitFlag != 5){
			throw new SchemeException("时间单位错误。");
		}
		
		StringBuffer sb = new StringBuffer();
		if(endTime != null){
			int delta = DateUtil.getTimeUsed(beginTime, endTime, timeUnitFlag, includeFirstDay);
			sb.append("从 ").append(AppsGlobals.formatDate(beginTime))
				.append(" 到 ").append(AppsGlobals.formatDate(endTime))
				.append(" 共 ").append(delta).append(" 个 ");
			if(timeUnitFlag == 4){
				sb.append("工作日。");
			}else{
				sb.append("自然天。");
			}
		}
		if(offset != null && offset != 0){
			Date date = DateUtil.getDate(beginTime, offset, timeUnitFlag, false);
			sb.append("\n从 ").append(AppsGlobals.formatDate(beginTime)).append(" 向");
			if(offset > 0){
				sb.append("前 ");
			}else{
				sb.append("后 ");
			}
			sb.append(Math.abs(offset)).append(" 个 ");
			if(timeUnitFlag == 4){
				sb.append("工作日 ");
			}else{
				sb.append("自然天 ");
			}
			sb.append(" 的日期是 ").append(AppsGlobals.formatDate(date)).append("。");
		}
		
		if(sb.length() == 0){
			sb.append("参数不完整，无法计算。");
		}
		
		return sb.toString();
	}
}
