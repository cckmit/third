/*
 * $Id: HangTaskWorkScheme.java 5971 2012-08-03 05:57:13Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.scheme.schemes.task;


import java.util.Date;

import cn.redflagsoft.base.bean.Work;

/**
 * ҵ����ͣ��
 * 
 * <pre>
 * /s/delayTask.jspa?objectServiceName=&taskType=&taskSN=&delayTime=&reasonCategory=&reason=&note=&matterIds[0]
 * 
 * delayTime ��������
 * reasonCategory ԭ�����
 * reason ԭ��
 * note ��ע
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
		
		return "ҵ����ͣ�ɹ�";
	}
}
