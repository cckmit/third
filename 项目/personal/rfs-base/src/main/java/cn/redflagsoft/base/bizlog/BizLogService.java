/*
 * $Id: BizLogService.java 5734 2012-05-18 12:36:56Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.bizlog;

import cn.redflagsoft.base.aop.Callback;
import cn.redflagsoft.base.aop.annotation.BizLog;
import cn.redflagsoft.base.audit.MethodCall;
import cn.redflagsoft.base.bean.RFSEntityObject;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.bean.Work;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface BizLogService {

	/**
	 * 处理调用指定方法的业务日志功能。
	 * 
	 * @param call
	 * @param bizLog
	 * @param callback
	 * @return
	 * @throws Throwable
	 */
	Object processBizLog(MethodCall call, BizLog bizLog, Callback callback) throws Throwable;
	
	/**
	 * 保存特定的业务日志。
	 * 
	 * @param object
	 * @param taskType
	 * @param workType
	 * @param defaultTask
	 * @param defaultWork
	 */
	void saveBizLog(RFSEntityObject object, int taskType, int workType, Task defaultTask, Work defaultWork, Long userId); 
}
