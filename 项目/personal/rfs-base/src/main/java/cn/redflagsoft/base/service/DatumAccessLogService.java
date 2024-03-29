/*
 * $Id: DatumAccessLogService.java 5395 2012-03-06 01:18:05Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;

import java.util.List;

import cn.redflagsoft.base.bean.DatumAccessLog;

/**
 * 资料访问日志处理类。
 * 
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface DatumAccessLogService {

	/**
	 * 保存一条日志信息。
	 * @param datumAccessLog
	 * @return
	 */
	DatumAccessLog saveAccessLog(DatumAccessLog datumAccessLog);
	
	/**
	 * 查询指定资料对应的所有访问日志集合。
	 * @param datumId 资料ID
	 * @return
	 */
	List<DatumAccessLog> findAccessLogs(Long datumId);
}
