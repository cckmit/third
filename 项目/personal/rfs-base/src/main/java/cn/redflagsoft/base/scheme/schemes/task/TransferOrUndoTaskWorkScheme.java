package cn.redflagsoft.base.scheme.schemes.task;

import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.bean.Work;

/***
 * ҵ��ת��  �� ҵ����
 * @author LiFeng
 *
 */
public class TransferOrUndoTaskWorkScheme extends AbstractTaskWorkScheme{
	/***
	 *  ҵ��ת�� 
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
		return "ҵ��ת�� �ɹ���";
	}
	
	/***
	 * ҵ����
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
		return "ҵ�����ɹ���";
	}
}
