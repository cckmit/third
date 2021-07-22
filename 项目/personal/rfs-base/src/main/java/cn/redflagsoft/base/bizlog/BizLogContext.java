/*
 * $Id: BizLogContext.java 5644 2012-05-10 09:15:59Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.bizlog;

import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.bean.Work;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class BizLogContext {

	private static final ThreadLocal<Task> task = new ThreadLocal<Task>();
	private static final ThreadLocal<Work> work = new ThreadLocal<Work>();
	
	public static Task getTask(){
		return task.get();
	}
	
	public static void setTask(Task task){
		BizLogContext.task.set(task);
	}
	
	public static Work getWork(){
		return work.get();
	}
	
	public static void setWork(Work work){
		BizLogContext.work.set(work);
	}
}
