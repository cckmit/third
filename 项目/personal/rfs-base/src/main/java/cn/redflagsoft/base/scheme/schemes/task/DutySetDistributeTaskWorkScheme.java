/*
 * $Id: TransmitOrDistributeTaskWorkScheme.java 6138 2012-11-29 02:20:40Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.scheme.schemes.task;

import org.springframework.util.Assert;

import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.bean.Work;

/***
 * ҵ�����ηַ���������趨��
 * @author lf
 *
 */
public class DutySetDistributeTaskWorkScheme extends AbstractTaskWorkScheme {
	
	private Task theTask;
	
	public Task getTheTask() {
		return theTask;
	}

	public void setTheTask(Task theTask) {
		this.theTask = theTask;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.AbstractWorkScheme#setWorkSN(java.lang.Long)
	 */
	@Override
	public void setWorkSN(Long workSN) {
		throw new UnsupportedOperationException("ת���в�֧������Work����");
	}

	public Object doScheme(){
		Task task2 = getTask();
		Task newTask = getTheTask();
		Assert.notNull(newTask, "Ҫ���õ�ҵ�������Ϊ��");
		Assert.notNull(newTask.getDutyerID(), "��������������");
		Assert.notNull(newTask.getDutyerLeader1Id(), "�������ÿ�������");
		Assert.notNull(newTask.getDutyerLeader2Id(), "�������÷ֹ��쵼");
		
		String format = String.format("ҵ����������  %s��%s��%s ���Ϊ %s�� %s��%s��", task2.getDutyerName(),task2.getDutyerLeader1Name(),task2.getDutyerLeader2Name(),newTask.getDutyerName(),
				newTask.getDutyerLeader1Name(),newTask.getDutyerLeader2Name());
		
		getTaskService().updateTaskDutyersInfo(task2, newTask);
		
		Work work2 = getWork();
		work2.setString1(newTask.getDutyerID()+"");
		work2.setString2(newTask.getDutyerLeader1Id()+"");
		work2.setString3(newTask.getDutyerLeader2Id()+"");

		work2.setSummary(format);
		getWorkService().updateWork(work2);
		return "ҵ������ɣ�";
	}
}
