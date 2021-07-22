/*
 * $Id: TransmitOrDistributeTaskWorkScheme.java 6138 2012-11-29 02:20:40Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme.schemes.task;

import org.springframework.util.Assert;

import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.bean.Work;

/***
 * 业务责任分发（变更、设定）
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
		throw new UnsupportedOperationException("转发中不支持设置Work对象");
	}

	public Object doScheme(){
		Task task2 = getTask();
		Task newTask = getTheTask();
		Assert.notNull(newTask, "要设置的业务对象不能为空");
		Assert.notNull(newTask.getDutyerID(), "必须设置责任人");
		Assert.notNull(newTask.getDutyerLeader1Id(), "必须设置科室主管");
		Assert.notNull(newTask.getDutyerLeader2Id(), "必须设置分管领导");
		
		String format = String.format("业务责任人由  %s、%s、%s 变更为 %s、 %s、%s。", task2.getDutyerName(),task2.getDutyerLeader1Name(),task2.getDutyerLeader2Name(),newTask.getDutyerName(),
				newTask.getDutyerLeader1Name(),newTask.getDutyerLeader2Name());
		
		getTaskService().updateTaskDutyersInfo(task2, newTask);
		
		Work work2 = getWork();
		work2.setString1(newTask.getDutyerID()+"");
		work2.setString2(newTask.getDutyerLeader1Id()+"");
		work2.setString3(newTask.getDutyerLeader2Id()+"");

		work2.setSummary(format);
		getWorkService().updateWork(work2);
		return "业务处理完成！";
	}
}
