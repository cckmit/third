/*
 * $Id: HangTaskWorkScheme.java 5971 2012-08-03 05:57:13Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme.schemes.task;


import java.util.Date;

import cn.redflagsoft.base.bean.Work;

/**
 * 业务暂停。
 * 
 * <pre>
 * /s/delayTask.jspa?objectServiceName=&taskType=&taskSN=&delayTime=&reasonCategory=&reason=&note=&matterIds[0]
 * 
 * delayTime 延期天数
 * reasonCategory 原因类别
 * reason 原因
 * note 备注
 * 
 * </pre>
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class HangTaskWorkScheme extends AbstractTaskWorkScheme {


	public Object doScheme(){
	
		//Task tk = getTaskService().getTask(getObjectId());
		
		getTask().setNote(getNote());
		getTaskService().hangTask(getTask(),new Date());
		
		
		Work work2 = getWork();
		work2.setString0(getReasonCategory());
		work2.setString1(getReason());
		work2.setString2(getClerkID() + "");
//		work2.setString4(delayTime + "");
		//work2.setRemark(getNote());
		work2.setString3(getNote());
		getWorkService().updateWork(work2);
		
		return "业务暂停成功";
	}
}
