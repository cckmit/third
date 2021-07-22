package cn.redflagsoft.base.scheme.schemes.task;

import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.bean.Work;

/***
 * 业务转出  或 业务撤销
 * @author LiFeng
 *
 */
public class TransferOrUndoTaskWorkScheme extends AbstractTaskWorkScheme{
	/***
	 *  业务转出 
	 */
	public Object doTransfer(){
		Task task2 = getTask();
		Work work2 = getWork();
		work2.setString0(getS0());
		work2.setString1(getS1());
		work2.setString3(task2.getStatus() + "");
		getWorkService().transferWork(work2);
		getTaskService().transferTask(task2, work2.getEndTime());
		setWork(null);
		return "业务转出 成功！";
	}
	
	/***
	 * 业务撤销
	 */
	public Object doUndo(){
		Task task2 = getTask();
		Work work2 = getWork();
		work2.setString0(getS0());
		work2.setString1(getS1());
		work2.setString3(task2.getStatus() + "");
		getWorkService().undoWork(work2);
		getTaskService().undoTask(task2, work2.getEndTime());
		setWork(null);
		return "业务撤销成功！";
	}
}
