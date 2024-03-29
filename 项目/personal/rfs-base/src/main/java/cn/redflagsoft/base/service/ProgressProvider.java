/*
 * $Id: ProgressProvider.java 6170 2013-01-08 07:15:41Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;

import cn.redflagsoft.base.bean.Progress;

/**
 * 进度报告提供者。
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface ProgressProvider {

	/**
	 * 返回进度报告。
	 * @return
	 */
	Progress getProgress();
}
