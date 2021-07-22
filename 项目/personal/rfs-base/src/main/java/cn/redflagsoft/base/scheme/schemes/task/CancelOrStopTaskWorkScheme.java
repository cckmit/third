package cn.redflagsoft.base.scheme.schemes.task;

import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.bean.Work;

/****
 * 	
 * 业务取消或者中止。
 *
 */
public class CancelOrStopTaskWorkScheme extends AbstractTaskWorkScheme{
	
	/**
	 * 	取消
	 */
	public Object doCancel(){
		Task task2 = getTask();
		Work work2 = getWork();
		work2.setString0(getS0());
		work2.setString1(getS1());
		work2.setString3(task2.getStatus() + "");
		getWorkService().cancelWork(work2);
		task2.setNote(getNote());
		getTaskService().cancelTask(task2, work2.getEndTime());
		setWork(null);
		return "业务取消成功！";
	}
	
	
	/***
	 * 中止
	 */
	public Object doStop(){
		Task task2 = getTask();
		Work work2 = getWork();
		work2.setString0(getS0());
		work2.setString1(getS1());
		work2.setString3(task2.getStatus() + "");
		getWorkService().stopWork(work2);
		task2.setNote(getNote());
		getTaskService().stopTask(task2, work2.getEndTime());
		setWork(null);
		return "业务中止成功！";
	}
}
