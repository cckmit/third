/*
 * $Id: RiskGradeChangeHandler.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;

import cn.redflagsoft.base.bean.Risk;

/**
 * @author Alex Lin
 *
 */
public interface RiskGradeChangeHandler {
	
	/**
	 * 当risk的grade发生改变时调用这个方法。
	 * 保存监控历史记录，发送各种提醒信息等。
	 * 
	 * 
	 * @param risk
	 * @param oldGrade
	 */
	void gradeChange(Risk risk, byte oldGrade);
}
