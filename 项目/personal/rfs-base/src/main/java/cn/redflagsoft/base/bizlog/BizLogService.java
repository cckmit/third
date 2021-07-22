/*
 * $Id: BizLogService.java 5734 2012-05-18 12:36:56Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
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
	 * �������ָ��������ҵ����־���ܡ�
	 * 
	 * @param call
	 * @param bizLog
	 * @param callback
	 * @return
	 * @throws Throwable
	 */
	Object processBizLog(MethodCall call, BizLog bizLog, Callback callback) throws Throwable;
	
	/**
	 * �����ض���ҵ����־��
	 * 
	 * @param object
	 * @param taskType
	 * @param workType
	 * @param defaultTask
	 * @param defaultWork
	 */
	void saveBizLog(RFSEntityObject object, int taskType, int workType, Task defaultTask, Work defaultWork, Long userId); 
}
